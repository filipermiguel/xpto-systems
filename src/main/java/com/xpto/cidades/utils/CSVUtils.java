package com.xpto.cidades.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CSVUtils {

	public static final Map<String, String> CSV_TO_ENTITY = new HashMap<>();

	static {
		CSV_TO_ENTITY.put("ibge_id", "ibge");
		CSV_TO_ENTITY.put("uf", "uf");
		CSV_TO_ENTITY.put("name", "name");
		CSV_TO_ENTITY.put("capital", "capital");
		CSV_TO_ENTITY.put("lon", "longitude");
		CSV_TO_ENTITY.put("lat", "latitude");
		CSV_TO_ENTITY.put("no_accents", "noAccentsName");
		CSV_TO_ENTITY.put("alternative_names", "alternativeNames");
		CSV_TO_ENTITY.put("microregion", "microRegion");
		CSV_TO_ENTITY.put("mesoregion", "mesoRegion");
	}

	private CSVUtils() {
	}

	public static String getStringValue(String valor) {
		return "".equals(valor) ? null : valor;
	}

	public static Integer getIntegerValue(String valor) {
		return Integer.parseInt(valor);
	}

	public static Boolean getBooleanValue(String valor) {
		return new Boolean(valor);
	}

	public static BigDecimal getBigDecimalValue(String valor) {
		return new BigDecimal(valor);
	}

}
