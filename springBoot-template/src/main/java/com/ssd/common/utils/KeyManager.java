package com.ssd.common.utils;

import java.security.Key;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author lus
 * @Date 2020/12/24 0024 16
 */
public class KeyManager {
    private static Map<String,Object> keyBox = new LinkedHashMap<>();

    public static Map<String, Key> getKey(String key){
        return (Map<String,Key>) keyBox.get(key);
    }
    public static void setKey(String key,Map<String,Key> obj){
        keyBox.put(key,obj);
    }
}
