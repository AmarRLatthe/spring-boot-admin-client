package com.amar.natural.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Amar
 *
 */
public class EnvPropertyReader {

	private static final Logger logger = LoggerFactory.getLogger(EnvPropertyReader.class.getName());

    private static Properties envProperties = null;

    private static long lastModified = 0L;
    private static String esbEnv = null;

    static {
        envProperties = loadProperties();
    }

    private static Properties loadProperties() {
        FileInputStream fis = null;
        try {
            esbEnv = System.getProperty("db.configuration.file.path");
            //esbEnv = System.getProperty("user.dir") + "//application.properties";
            if (esbEnv == null) {
                throw new Exception("Please set db.configuration.file.path");
            }

            File f = new File(esbEnv);
            Properties prop = new Properties();
            logger.info("Logging environment properties from :" + esbEnv);
            fis = new FileInputStream(esbEnv);
            prop.load(fis);
            lastModified = f.lastModified();
            return prop;
        } catch (Exception ex) {
            logger.info("Exception while loading environment properties", ex);
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ex) {
                    //Logger.getLogger(EnvPropertyReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static String getValue(String key) {
        File f = new File(esbEnv);
        logger.info("File Last modified: " + f.lastModified() + " ; variable lastModified: " + lastModified);
        if (f.lastModified() > lastModified) {
            envProperties = loadProperties();
        }

        return envProperties.getProperty(key);

    }

    public static void main(String args[]) {

//        String key="Fitbug.emailto";
//        logger.info("Value is:" + EnvPropertyReader.getValue(key));
        System.out.println(":::::::::::::::::::::: " +System.getProperty("user.dir"));
    }
}
