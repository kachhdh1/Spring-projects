package com.dk.job.service;

import static com.dk.job.util.RuleConfigConstants.AUTHENTICATE_BODY_TOKEN;
import static com.dk.job.util.RuleConfigConstants.AUTHORIATION;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.dk.job.bean.DataDictionary;
import com.dk.job.bean.DataDictionaryBody;
import com.dk.job.bean.ResponseObj;
import com.dk.job.bean.RuleCsv;
import com.dk.job.bean.User;
import com.dk.job.json.RuleJson;
import com.dk.job.util.RuleConfigJobUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UIService {

	@Value("${url.create.rule}")
	private String createRuleUrl;

	@Value("${url.get.dd}")
	private String getDDUrl;

	@Value("${model.type}")
	private String modelType;
	
	@Value("${public.key}")
	private String publicKey;
	
	@Value("${hscale.password}")
	private String hscalePassword;
	
	@Value("${hscale.userName}")
	private String hscaleUserName;

	@Autowired
	ObjectMapper mapper;
	
	private static final Logger logger = LogManager.getLogger(UIService.class);

	public ResponseObj postJsonMessage(RuleJson payload, String casToken) throws JsonProcessingException {
		ResponseObj responseObj = new ResponseObj();
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = configureHeaders(casToken);

		try {
			HttpEntity<String> entity = new HttpEntity<>(
					mapper.writeValueAsString(payload), headers);
			ResponseEntity<String> response = restTemplate.exchange(
					createRuleUrl, HttpMethod.POST, entity, String.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				logger.debug("Response body :" + response.getBody());
				responseObj = extractAttributeFromResponse(responseObj, response.getBody());
			} 
		} catch (HttpClientErrorException e) {
			responseObj = extractAttributeFromResponse(responseObj, e.getResponseBodyAsString());
		}
		return responseObj;

	}

	private HttpHeaders configureHeaders(String casToken) {
		final HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType((MediaType.APPLICATION_JSON));
		headers.set(AUTHORIATION, casToken);
		return headers;
	}

	public String authenticateUser(String authenticateUserUrl) {

		String cas_token = "";
		try {
			String encyPass=RuleConfigJobUtil.encryptPassword(hscalePassword, publicKey);
			User user = new User(hscaleUserName, encyPass);
			logger.debug("Sending authentication request for user : " + user);
			RestTemplate restTemplate = new RestTemplate();
			final HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType((MediaType.APPLICATION_JSON));
			try {
				HttpEntity<User> entity = new HttpEntity<>(user, headers);
				ResponseEntity<String> response = restTemplate.exchange(
						authenticateUserUrl, HttpMethod.POST, entity,
						String.class);
				JSONObject jObj = new JSONObject(response.getBody());
				//logger.debug(jObj.get(AUTHENTICATE_BODY_TOKEN));
				cas_token = jObj.getString(AUTHENTICATE_BODY_TOKEN);
				logger.debug("Authentication successful..");
			} catch (HttpClientErrorException ex) {
				logger.error("Error occured while authenticating user:"+ex.toString());
			} catch (IllegalArgumentException illegalEx) {
				logger.error(illegalEx.getMessage());
			} 
		} catch (Exception e) {
			logger.error("Error occured while authenticating user"+e.getMessage());
		}

		return cas_token;
	}

	public DataDictionary getDataDictionary(String casToken, String sourceSystemCode) {
		DataDictionary dd = new DataDictionary();
		RestTemplate restTemplate = new RestTemplate();
		DataDictionaryBody ddBody = new DataDictionaryBody(modelType,
				sourceSystemCode);
		HttpHeaders headers = configureHeaders(casToken);

		try {
			HttpEntity<String> entity = new HttpEntity<>(
					mapper.writeValueAsString(ddBody), headers);
			ResponseEntity<String> response = restTemplate.exchange(getDDUrl,
					HttpMethod.POST, entity, String.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				logger.debug("Post Success getting DD, status code :"+response.getStatusCode());
				mapper.configure(
						DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
				dd = mapper.readValue(response.getBody(), DataDictionary.class);
				//logger.debug("DD Entities :: " + dd.getDdEntities());
			} else {
				logger.error("Problem while fetching DD from hscale service");
			}
		} catch (HttpClientErrorException | IOException e) {
			logger.error("Exception while fetching DD from hscale service "+e.getMessage());
		}
		return dd;

	}

	public ResponseObj extractAttributeFromResponse(ResponseObj responseObj,
			String responseJsonBody) {
		mapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
		try {
			responseObj= mapper.readValue(responseJsonBody, ResponseObj.class);
		} catch (IOException e) {
			logger.error("Error while parsing response from Hscale UI "+e.getCause());
		}
		return responseObj;
	}
	
	/**
	 * function to fetch DD for all the supplied source systems 
	 * @param casToken
	 * @param listFromCsv
	 * @return Map of source system with dd
	 */
	public Map<String,DataDictionary> getDDForSourceSystems(String casToken,List<RuleCsv> listFromCsv){
		List<String> sourceSystems = listFromCsv
			.stream()
			.map(ruleCsv -> ruleCsv.getSourceSystemCode())
			.distinct()
			.collect(Collectors.toList());
		Map<String,DataDictionary> ddMap = new HashMap<>();
		for(String sourceSystem : sourceSystems){
			ddMap.put(sourceSystem,getDataDictionary(casToken, sourceSystem));
		}
		return ddMap;
	}

}
