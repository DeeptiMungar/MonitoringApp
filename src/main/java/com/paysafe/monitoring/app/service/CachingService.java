package com.paysafe.monitoring.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.paysafe.monitoring.app.beans.MonitoringData;

@Service
public class CachingService {

	private static List<MonitoringData> store = new ArrayList<MonitoringData>();

	public static List<MonitoringData> getStore() {
		return store;
	}

	public static void setStore(List<MonitoringData> store) {
		CachingService.store = store;
	}

	@CachePut(value = "monitoringData", key = "#dateTime")
	public MonitoringData putMonitoringData(MonitoringData data) {
		store.add(data);
		return data;
	}

}
