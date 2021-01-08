package com.ssd.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssd.common.lang.PageHandler;
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
public interface IMonitorPlateService extends IService<MonitorPlate> {

    Integer findCount(PageHandler pageHandler, Map<String, Object> map);

    IPage<MonitorPlate> findByPage(PageHandler pageHandler, Map<String, Object> map);

    MonitorPlate getByCode(String plateCode);
}
