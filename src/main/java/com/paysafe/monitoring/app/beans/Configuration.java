package com.paysafe.monitoring.app.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@Component
public class Configuration {

	@Value("${paysafeURL}")
	private String paysafeURL;

	public String getPaysafeURL() {
		return paysafeURL;
	}

	public void setPaysafeURL(String paysafeURL) {
		this.paysafeURL = paysafeURL;
	}

}
