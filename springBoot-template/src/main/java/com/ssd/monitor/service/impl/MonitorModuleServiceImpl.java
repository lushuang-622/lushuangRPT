package com.ssd.monitor.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssd.common.lang.PageHandler;
import com.ssd.monitor.entity.MonitorModule;
import com.ssd.monitor.dao.IMonitorModuleDao;
import com.ssd.monitor.entity.MonitorPlate;
import com.ssd.monitor.service.IMonitorModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
@Service
@DS("master")
public class MonitorModuleServiceImpl extends ServiceImpl<IMonitorModuleDao, MonitorModule> implements IMonitorModuleService {

    @Resource
    IMonitorModuleDao moduleDao;

    @Override
    public Integer findCount(PageHandler pageHandler,Map<String, Object> map) {
        QueryWrapper<MonitorModule> query = new QueryWrapper<>();
        query.allEq(pageHandler.getQueryMap());
        if(pageHandler.getLikeMap() != null){
            pageHandler.getLikeMap().forEach((key, value) -> {
                query.and(wrapper -> wrapper.like(key,value));
            });
        }
        return moduleDao.selectCount(query);
    }

    @Override
    public IPage<MonitorModule> findByPage(PageHandler pageHandler, Map<String, Object> map) {
        Page<MonitorModule> page = new Page<>(pageHandler.getLimit(), pageHandler.getSize());
        QueryWrapper<MonitorModule> query = new QueryWrapper<>();
        query.allEq(pageHandler.getQueryMap());
        if(pageHandler.getLikeMap() != null){
            pageHandler.getLikeMap().forEach((key, value) -> {
                query.and(wrapper -> wrapper.like(key,value));
            });
        }
        if("desc".equals(pageHandler.getSortName())){
            query.orderByDesc(pageHandler.getSortName());
        }else{
            query.orderByAsc(pageHandler.getSortName());
        }
        IPage<MonitorModule> pageResult = moduleDao.selectPage(page, query);
        return pageResult;
    }

    @Override
    public MonitorModule getByCode(String moduleCode) {
        Map<String, Object> collomMap = new HashMap<>();
        collomMap.put("module_code",moduleCode);
        collomMap.put("enable",1);
        List<MonitorModule> monitorModules = moduleDao.selectByMap(collomMap);
        return monitorModules.size() > 0 ? monitorModules.get(0) : null;
    }
}
