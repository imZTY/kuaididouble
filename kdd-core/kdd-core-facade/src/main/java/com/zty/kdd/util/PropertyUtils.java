package com.zty.kdd.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {
    private final static transient Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    private final static Map<String, Properties> fileNameMap = new HashMap<>();

    public static String getValue(String propertyFileName, String key) {
        Properties prop;
        if (fileNameMap.containsKey(propertyFileName)) {
            prop = fileNameMap.get(propertyFileName);
        } else {
            prop = new Properties();
            InputStream in = PropertyUtils.class.getClassLoader().getResourceAsStream(propertyFileName);
            try {
                prop.load(in);
                in.close();
                fileNameMap.put(propertyFileName, prop);
            } catch (IOException e) {
                logger.error("获取配置异常", e);
                return null;
            }
        }

        return prop.getProperty(key);
    }

}
