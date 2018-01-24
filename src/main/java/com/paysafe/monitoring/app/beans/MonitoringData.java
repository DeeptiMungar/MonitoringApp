package com.paysafe.monitoring.app.beans;

import org.springframework.stereotype.Component;

@Component
// @Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class MonitoringData {

	private String status;

	private String dateTime;

	public MonitoringData() {
	}

	public MonitoringData(String status, String dateTime) {
		this.status = status;
		this.dateTime = dateTime;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String localDateTime) {
		this.dateTime = localDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
