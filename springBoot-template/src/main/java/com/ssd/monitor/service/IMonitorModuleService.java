package com.ssd.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ssd.common.lang.PageHandler;
import com.ssd.monitor.entity.MonitorModule;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssd.monitor.entity.MonitorPlate;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
public interface IMonitorModuleService extends IService<MonitorModule> {

    Integer findCount(PageHandler pageHandler, Map<String, Object> map);

    IPage<MonitorModule> findByPage(PageHandler pageHandler, Map<String, Object> map);

    MonitorModule getByCode(String moduleCode);
}
