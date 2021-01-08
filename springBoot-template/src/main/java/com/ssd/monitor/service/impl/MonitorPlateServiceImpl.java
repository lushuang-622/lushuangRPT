package com.ssd.monitor.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssd.common.lang.PageHandler;
import com.ssd.monitor.dao.IMonitorPlateDao;
import com.ssd.monitor.entity.MonitorPlate;
import com.ssd.monitor.service.IMonitorPlateService;
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
public class MonitorPlateServiceImpl extends ServiceImpl<IMonitorPlateDao, MonitorPlate> implements IMonitorPlateService {

    @Resource
    IMonitorPlateDao plateDao;

    @Override
    public Integer findCount(PageHandler pageHandler, Map<String, Object> map) {
        QueryWrapper<MonitorPlate> query = new QueryWrapper<>();
        query.allEq(pageHandler.getQueryMap());
        if(pageHandler.getLikeMap() != null){
            pageHandler.getLikeMap().forEach((key, value) -> {
                query.and(wrapper -> wrapper.like(key,value));
            });
        }
        return plateDao.selectCount(query);
    }

    @Override
    public IPage<MonitorPlate> findByPage(PageHandler pageHandler, Map<String, Object> map) {
        Page<MonitorPlate> page = new Page<>(pageHandler.getLimit(), pageHandler.getSize());
        QueryWrapper<MonitorPlate> query = new QueryWrapper<>();
        query.allEq(pageHandler.getQueryMap());
        if(pageHandler.getLikeMap() != null){
            pageHandler.getLikeMap().forEach((key, value) -> {
                query.and(wrapper -> wrapper.like(key,value));
            });
        }
        if ("desc".equals(pageHandler.getSortName())) {
            query.orderByDesc(pageHandler.getSortName());
        } else {
            query.orderByAsc(pageHandler.getSortName());
        }
        IPage<MonitorPlate> pageResult = plateDao.selectPage(page, query);
        return pageResult;
    }

    @Override
    public MonitorPlate getByCode(String plateCode) {
        Map<String, Object> collomMap = new HashMap<>();
        collomMap.put("palte_code", plateCode);
        collomMap.put("enable", 1);
        List<MonitorPlate> monitorPlates = plateDao.selectByMap(collomMap);
        return monitorPlates.size() > 0 ? monitorPlates.get(0) : null;
    }
}
