package com.paysafe.monitoring.app.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paysafe.monitoring.app.beans.Configuration;
import com.paysafe.monitoring.app.beans.MonitoringData;
import com.paysafe.monitoring.app.utils.JSONUtils;
import com.paysafe.monitoring.app.utils.Rest;
import com.paysafe.monitoring.app.utils.StatusConstants;

@Service
public class MonitorService {

	private static final Logger log = LoggerFactory.getLogger(MonitorService.class);

	@Autowired
	Configuration configuration;

	@Autowired
	CachingService cacheService;

	@Autowired
	MonitoringData monitoringData;

	@Autowired
	Rest rest;

	/**
	 * This method is used to check the status of service.
	 * 
	 * @return
	 */
	public JSONObject checkService() {
		log.info("ENTER: Service's checkService()");
		String url = configuration.getPaysafeURL();
		JSONObject response = new JSONObject();
		try {
			String result = rest.get(url);
			response = (JSONObject) JSONUtils.covertToJSON(result);
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
			String date = DATE_FORMAT.format(new Date());
			MonitoringData data = new MonitoringData(response.getString("status").toString(), date);
			cacheService.putMonitoringData(data);
			return response;
		} catch (Exception e) {
			log.error("Service Exception is   " + e.getLocalizedMessage(), e);
			response.put(StatusConstants.ERROR_LABEL, e.getLocalizedMessage());
			return response;
		}
	}

	/**
	 * This method is used to get the list of monitoring data which displays the
	 * status and time.
	 * 
	 * @return
	 */
	public JSONObject getMonitorData() {
		log.info("ENTER: Service's getMonitorData()");
		JSONObject response = new JSONObject();
		try {
			cacheService.getStore().forEach(entry -> {
				log.info("status : " + entry.getStatus() + " dateTime : " + entry.getDateTime());
			});
			response.put(StatusConstants.STATUS_LABEL, cacheService.getStore());
			return response;
		} catch (Exception e) {
			log.error("Service Exception is   " + e.getLocalizedMessage(), e);
			response.put(StatusConstants.ERROR_LABEL, e.getLocalizedMessage());
			return response;
		}

	}

	public Runnable checkServiceRunnable() {
		log.info("ENTER : Service's checkServiceRunnable()");
		return () -> log.info(checkService().toString());

	}

}
