package com.ssd.common.utils;

import com.ssd.config.DruidConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author lus
 * @Date 2020/12/25 0025 17
 */
public class LocalLogUtils {
    private static final Logger log = LoggerFactory.getLogger(DruidConfiguration.class);

    public static void info(String message){
        log.info(message);
    }

    public static void error(String message,Exception e){
        log.error(message,e);
    }

    public static void debug(String message){
        log.debug(message);
    }

    public static void warn(String message){
        log.warn(message);
    }

    public static void trace(String message){
        log.trace(message);
    }
}
