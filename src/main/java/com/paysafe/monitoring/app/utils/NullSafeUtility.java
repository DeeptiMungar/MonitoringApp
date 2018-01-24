package com.paysafe.monitoring.app.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class NullSafeUtility {

	public boolean isStringNullBlank(String str) {
		boolean response = true;

		if (str != null && !"".equals(str)) {
			response = false;
		}
		return response;
	}

	public boolean isStringNull(String str) {
		boolean response = true;

		if (str != null) {
			response = false;
		}
		return response;
	}

	public boolean isObjectNull(Object obj) {
		boolean response = true;

		if (obj != null) {
			response = false;
		}
		return response;
	}

	public boolean isListNullBlank(List objs) {
		boolean response = true;

		if (objs != null && !objs.isEmpty()) {
			response = false;
		}

		return response;
	}

	public boolean isListNull(List<Object> objs) {
		boolean response = true;

		if (objs != null) {
			response = false;
		}

		return response;
	}

	public boolean isMapNullBlank(Map map) {
		boolean response = true;

		if (map != null && !map.isEmpty()) {
			response = false;
		}
		return response;
	}

	public boolean isMapNull(Map map) {
		boolean response = true;

		if (map != null) {
			response = false;
		}
		return response;
	}

	public boolean isSetNullBlank(Set objs) {
		boolean response = true;

		if (objs != null && !objs.isEmpty()) {
			response = false;
		}

		return response;
	}

	public boolean isSetNull(Set<Object> objs) {
		boolean response = true;

		if (objs != null) {
			response = false;
		}

		return response;
	}

}
