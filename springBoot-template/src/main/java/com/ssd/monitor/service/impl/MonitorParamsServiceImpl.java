package com.ssd.monitor.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssd.common.lang.PageHandler;
import com.ssd.monitor.entity.MonitorModule;
import com.ssd.monitor.entity.MonitorParams;
import com.ssd.monitor.dao.IMonitorParamsDao;
import com.ssd.monitor.service.IMonitorParamsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.monitor.Monitor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
@Service
@DS("master")
public class MonitorParamsServiceImpl extends ServiceImpl<IMonitorParamsDao, MonitorParams> implements IMonitorParamsService {
    @Resource
    IMonitorParamsDao paramsDao;

    @Override
    public Integer findCount(PageHandler pageHandler,Map<String, Object> map) {
        QueryWrapper<MonitorParams> query = new QueryWrapper<>();
        query.allEq(pageHandler.getQueryMap());
        if(pageHandler.getLikeMap() != null){
            pageHandler.getLikeMap().forEach((key, value) -> {
                query.and(wrapper -> wrapper.like(key,value));
            });
        }
        return paramsDao.selectCount(query);
    }

    @Override
    public IPage<MonitorParams> findByPage(PageHandler pageHandler, Map<String, Object> map) {
        Page<MonitorParams> page = new Page<>(pageHandler.getLimit(), pageHandler.getSize());
        QueryWrapper<MonitorParams> query = new QueryWrapper<>();
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
        IPage<MonitorParams> pageResult = paramsDao.selectPage(page, query);
        return pageResult;
    }

    @Override
    public MonitorParams getByCode(String paramCode) {
        Map<String, Object> collomMap = new HashMap<>();
        collomMap.put("param_code",paramCode);
        collomMap.put("enable",1);
        List<MonitorParams> monitorParams = paramsDao.selectByMap(collomMap);
        return monitorParams.size() > 0 ? monitorParams.get(0) : null;
    }
}
