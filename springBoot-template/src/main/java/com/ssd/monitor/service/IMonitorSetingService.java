package com.ssd.monitor.service;

import com.alibaba.fastjson.JSONObject;
import com.ssd.monitor.entity.MonitorParams;

import java.util.Map;

/**
 * 设置服务接口
 *
 * @author lus
 * @Date 2020/12/25 0025 17
 */
public interface IMonitorSetingService {
    boolean setMonitorModel(Map<String, Object> map);

    MonitorParams getMonitorModel(Map<String, Object> map);

    JSONObject getSetingByPlateCode(Map<String, Object> map);

    JSONObject getSetingByModuleCode(Map<String, Object> map);
}
