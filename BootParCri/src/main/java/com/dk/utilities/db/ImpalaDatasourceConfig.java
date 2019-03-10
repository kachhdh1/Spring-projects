package com.dk.utilities.db;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
/*import javax.annotation.PostConstruct;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;*/

/**
 * This class basically creates the connection for the Impala database.
 */
@Configuration
public class ImpalaDatasourceConfig {
	
	@Autowired
    private Environment environment;

    /*private static final Logger LOGGER = LoggerFactory
            .getLogger(ImpalaDatasourceConfig.class);

    private static final String JAAS_FILE = "impala-action-jaas.conf";

    *//**
     * This is invoked post the bean is initialized and it basically creates the JAAS config file and does some
     * configuration which is pre-requisite for connecting to Kerberos Enabled Impala Server.
     *//*
    @PostConstruct
    public void init() {
        String keytabFile = environment.getRequiredProperty("impala.keytab.file.location");
        String keytabPrincipal = environment.getRequiredProperty("impala.keytab.principal");

        System.out.println("keytabFile " + keytabFile +
                " keytabPrincipal " + keytabPrincipal);

        if (keytabFile != null && Files.notExists(Paths.get(keytabFile))) {
            LOGGER.error("Specified [keytab-file] does not exist");
            throw new RuntimeException("Specified [keytab-file] does not exist");
        }

        try {
            createJaasFileConf(keytabFile, keytabPrincipal);
            System.setProperty("java.security.auth.login.config", JAAS_FILE);
            System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
            if (System.getProperty("java.security.krb5.conf") == null) {
                System.setProperty("java.security.krb5.conf", "/etc/krb5.conf");
            }

        } catch (IOException ioe) {
            throw new RuntimeException("Error file creating " + JAAS_FILE, ioe);
        }
    }

    *//**
     * The DataSource which gets created based on the configuration.
     *
     * @return the DataSource for the impala project.
     *//*
    @Bean(name = "impalaDataSource")
    public DataSource dataSource() {
        String url = String.format(environment.getRequiredProperty("impala.datasource.url"),
                environment.getRequiredProperty(System.getProperty(Application.SOURCE_SYSTEM_NAME)));
        String driver = environment.getRequiredProperty("impala.datasource.driver-class-name");
        System.out.println(" IMPALA url " + url + " driver " + driver);
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(environment.getRequiredProperty("impala.datasource.driver-class-name"));
        ds.setUrl(url);
        return ds;
    }

    *//**
     * Used to create JDBCTemplate for the Impala connection.
     *
     * @param dataSource the DataSource for the impala connection.
     * @return the JDBCTemplate
     *//*
    @Bean(name = "impalaJdbcTemplate")
    public JdbcTemplate hiveJdbcTemplate(@Qualifier("impalaDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    *//**
     * Used to create JAAS configuration file on the current working path.
     *
     * @param keytabFile        the keytab file
     * @param kerberosPrincipal the keytab principal
     * @throws IOException in case there is error in generating the file
     *//*
    private void createJaasFileConf(String keytabFile, String kerberosPrincipal) throws IOException {
        PrintWriter writer = new PrintWriter(JAAS_FILE, "UTF-8");
        writer.println("com.sun.security.jgss.initiate {");
        writer.println("  com.sun.security.auth.module.Krb5LoginModule required");
        writer.println("  useKeyTab=true");
        writer.println("  keyTab=\"" + keytabFile + "\"");
        writer.println("  storeKey=true");
        writer.println("  useTicketCache=false");
        writer.println("  principal=\"" + kerberosPrincipal + "\"");
        writer.println("  doNotPrompt=true");
        writer.println("  debug=true;");
        writer.println("};");
        writer.close();
    }*/
	
	/**
	 * Local setup for second Database
	 */
	@Bean(name = "impalaDataSource")
    public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(environment.getRequiredProperty("imp.datasource.driver-class-name"));
        ds.setUrl(environment.getRequiredProperty("imp.datasource.url"));
        ds.setUsername(environment.getRequiredProperty("imp.datasource.username"));
        ds.setPassword(environment.getRequiredProperty("imp.datasource.password"));
        return ds;
    }
	
	@Bean(name = "impalaJdbcTemplate")
    public JdbcTemplate hiveJdbcTemplate(@Qualifier("impalaDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
