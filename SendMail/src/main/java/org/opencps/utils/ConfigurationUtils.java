package org.opencps.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationUtils {

	public static Properties getInfoConfig() {
		Properties props = new Properties();
		try {
			FileInputStream fis = new FileInputStream(ConstantsUtils.PROPERETIES_CONFIG_PATH);
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}
}
