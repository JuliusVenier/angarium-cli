package com.angarium.service;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigService {


    public static final String ANGARIUM_CONF_NAME = "angarium.conf";
    public static final String DEFAULT_CONF =
"""
username = ""
password = ""
host = "127.0.0.1"
port = 8080
""";

    public Config read() throws IOException {
        File homeConfig = new File(Paths.get(System.getProperty("user.home"), ANGARIUM_CONF_NAME).toUri());
        if(!homeConfig.exists()) {
            homeConfig.createNewFile();
            Files.writeString(homeConfig.toPath(), DEFAULT_CONF);
        }
        return ConfigFactory.load(ConfigFactory.parseFile(homeConfig));
    }
}
