package com.paysafe.monitoring.app.controller;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paysafe.monitoring.app.service.MonitorService;
import com.paysafe.monitoring.app.utils.NullSafeUtility;
import com.paysafe.monitoring.app.utils.StatusConstants;

@RestController
public class PaysafeController {

	private static final Logger log = LoggerFactory.getLogger(PaysafeController.class);

	@Autowired
	MonitorService service;	

	@Autowired
	private NullSafeUtility nullSafeUtility;

	@Autowired
	TaskScheduler taskScheduler;

	ScheduledFuture<?> scheduledFuture;

	@RequestMapping("/")
	public String base() {
		log.info("ENTER: Controller's base()");
		return "Welcome " + new Date().toString();
	}

	@RequestMapping(value = "/start", method = RequestMethod.POST, produces = { "application/json" })
	ResponseEntity<String> startMonioring(@RequestParam(value = "interval", required = true) int interval) {
		log.info("ENTER: Controller's startMonioring()");
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			long period = TimeUnit.MINUTES.toMillis(interval);
			PeriodicTrigger periodicTrigger = new PeriodicTrigger(period);
			scheduledFuture = taskScheduler.schedule(service.checkServiceRunnable(), periodicTrigger);
			return new ResponseEntity<String>(httpStatus);
		} catch (Exception e) {
			log.error("Controller exception is   " + e.getLocalizedMessage(), e);
			JSONObject error = new JSONObject();
			error.put(StatusConstants.ERROR_LABEL, e.getLocalizedMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<String>(error.toString(), httpStatus);
		}

	}

	@RequestMapping(value = "/getMonitoringData", method = RequestMethod.GET, produces = { "application/json" })
	ResponseEntity<String> getMonitoringData() {
		log.info("ENTER: Controller's getMonitoringData()");
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			return new ResponseEntity<String>(service.getMonitorData().get(StatusConstants.STATUS_LABEL).toString(), httpStatus);
		} catch (Exception e) {
			log.error("Controller exception is   " + e.getLocalizedMessage(), e);
			JSONObject error = new JSONObject();
			error.put(StatusConstants.ERROR_LABEL, e.getLocalizedMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<String>(error.toString(), httpStatus);
		}
	}

	@RequestMapping(value = "/stop", method = RequestMethod.GET, produces = { "application/json" })
	ResponseEntity<String> stopMonitoring() {
		log.info("ENTER: Controller's stopMonioring()");
		HttpStatus httpStatus = HttpStatus.OK;
		try {
			if (!nullSafeUtility.isObjectNull(scheduledFuture)) {
				scheduledFuture.cancel(false);
			}
			return new ResponseEntity<String>(httpStatus);
		} catch (Exception e) {
			log.error("Controller exception is   " + e.getLocalizedMessage(), e);
			JSONObject error = new JSONObject();
			error.put(StatusConstants.ERROR_LABEL, e.getLocalizedMessage());
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<String>(error.toString(), httpStatus);
		}
	}

}
