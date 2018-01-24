package com.paysafe.monitoring.app.utils;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paysafe.monitoring.app.beans.Configuration;

@Component
public class Rest {
	private static final Logger log = LoggerFactory.getLogger(Rest.class);

	@Autowired
	Configuration configuration;

	private URIBuilder getBuilder(String url) {
		URIBuilder builder = new URIBuilder();
		builder.setScheme(url.substring(0, url.indexOf("://"))).setHost(url.substring(url.indexOf("://") + 3));
		log.info("Url Build=>" + builder.toString());
		return builder;
	}

	public String get(String url) throws Exception {
		log.info("ENTER: Rest's GET");
		HttpGet request = null;
		HttpResponse response = null;
		URIBuilder builder = getBuilder(url);
		HttpClient client = HttpClients.createDefault();
		try {
			request = new HttpGet(builder.build());
			response = client.execute(request);
			log.info("Response StatusLine=>" + response.getStatusLine());
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception(response.getStatusLine().getReasonPhrase());
			}
			log.info("EXIT: Rest's GET");
			return EntityUtils.toString(response.getEntity());
		} catch (URISyntaxException e) {
			log.error("URISyntaxException==>" + e.getMessage());
			throw new Exception(e);
		} catch (ParseException e) {
			throw new Exception(e);
		} catch (IOException e) {
			log.error("IOException==>" + e.getMessage());
			throw new Exception(e);
		}
	}

}
