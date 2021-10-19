package com.heaerie.common;

import com.heaerie.common.PillarPropService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SparrowPropService {
    private static SparrowPropService singleton;
    private static final String FILE_PATH = "/etc/heaerie/sparrow/conf/sparrow.conf";
    static Properties prop;
    Map<String, String> map = new HashMap();

    private SparrowPropService() throws IOException {
        prop = readPropertiesFile("/etc/heaerie/sparrow/conf/sparrow.conf");
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;

        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException var7) {
            var7.printStackTrace();
        } finally {
            fis.close();
        }

        return prop;
    }

    public static SparrowPropService init() throws IOException {
        singleton = new SparrowPropService();
        return singleton;
    }

    public static String get(String key, String notFound) {
        return prop.getProperty(key, notFound);
    }

    public static Integer getNumber(String key, Integer notFound) {
        String value = prop.getProperty(key, notFound.toString());
        return Integer.parseInt(value);
    }
}
