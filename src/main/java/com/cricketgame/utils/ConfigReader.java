package com.cricketgame.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    public static Properties getProperties(String filename) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filename);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        return properties;
    }


}

