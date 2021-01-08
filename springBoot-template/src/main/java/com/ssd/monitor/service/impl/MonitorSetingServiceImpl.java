package com.ssd.monitor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ssd.monitor.dao.IMonitorModuleDao;
import com.ssd.monitor.dao.IMonitorParamsDao;
import com.ssd.monitor.dao.IMonitorPlateDao;
import com.ssd.monitor.entity.MonitorModule;
import com.ssd.monitor.entity.MonitorParams;
import com.ssd.monitor.entity.MonitorPlate;
import com.ssd.monitor.service.IMonitorSetingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页配置服务类
 *
 * @author lus
 * @Date 2020/12/25 0025 17
 */
@Service
@DS("master")
public class MonitorSetingServiceImpl implements IMonitorSetingService {

    @Resource
    IMonitorPlateDao plateDao;

    @Resource
    IMonitorModuleDao moduleDao;

    @Resource
    IMonitorParamsDao paramsDao;

    @Override
    public boolean setMonitorModel(Map<String, Object> map) {
        UpdateWrapper<MonitorParams> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("module_code",map.get("moduleCode").toString())
                .eq("param_code",map.get("paramCode").toString());
        MonitorParams monitorParams = new MonitorParams();
        monitorParams.setParamValue(map.get("paramValue").toString());
        int update = paramsDao.update(monitorParams, updateWrapper);
        if(update > 0){
            return true;
        }
        return false;
    }

    @Override
    public MonitorParams getMonitorModel(Map<String, Object> map) {
        List<MonitorParams> monitorParams = paramsDao.selectByMap(map);
        return monitorParams.size() > 0 ? monitorParams.get(0) : null;
    }

    @Override
    public JSONObject getSetingByPlateCode(Map<String, Object> map) {
        JSONObject data = new JSONObject();
        Map<String, Object> collomMap = new HashMap<>();
        collomMap.put("plate_code",map.get("plateCode"));
        List<MonitorPlate> monitorPlates = plateDao.selectByMap(collomMap);
        data.put("monitorPlates",monitorPlates);
        List<MonitorModule> monitorModules = moduleDao.selectByMap(collomMap);
        data.put("monitorModules",monitorModules);
        List<String> moduleCodes = new ArrayList<>();
        monitorModules.stream().forEach(m ->{
            moduleCodes.add(m.getModuleCode());
        });
        QueryWrapper<MonitorParams> queryWrapper = new QueryWrapper<MonitorParams>();
        queryWrapper.in("module_code",moduleCodes);
        List<MonitorParams> monitorParams = paramsDao.selectList(queryWrapper);
        data.put("monitorParams",monitorParams);
        return data;
    }

    @Override
    public JSONObject getSetingByModuleCode(Map<String, Object> map) {
        JSONObject data = new JSONObject();
        Map<String, Object> collomMap = new HashMap<>();
        collomMap.put("plate_code",map.get("plateCode"));
        collomMap.put("module_code",map.get("moduleCode"));
        List<MonitorModule> monitorModules = moduleDao.selectByMap(collomMap);
        data.put("monitorModules",monitorModules);
        collomMap.remove("plate_code");
        List<MonitorParams> monitorParams = paramsDao.selectByMap(collomMap);
        data.put("monitorParams",monitorParams);
        return data;
    }
}
