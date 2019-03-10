package com.dk.job.json;

public class LookupTableColumn {
	
	private String beforeCodeColumn;
	private String afterCodeColumn;
	
	public String getBeforeCodeColumn() {
		return beforeCodeColumn;
	}
	public LookupTableColumn(String beforeCodeColumn, String afterCodeColumn) {
		super();
		this.beforeCodeColumn = beforeCodeColumn;
		this.afterCodeColumn = afterCodeColumn;
	}
	public void setBeforeCodeColumn(String beforeCodeColumn) {
		this.beforeCodeColumn = beforeCodeColumn;
	}
	public String getAfterCodeColumn() {
		return afterCodeColumn;
	}
	public void setAfterCodeColumn(String afterCodeColumn) {
		this.afterCodeColumn = afterCodeColumn;
	}
	
	

}
