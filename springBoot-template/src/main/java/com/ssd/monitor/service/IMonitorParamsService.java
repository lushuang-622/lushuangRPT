package com.ssd.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ssd.common.lang.PageHandler;
import com.ssd.monitor.entity.MonitorParams;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
public interface IMonitorParamsService extends IService<MonitorParams> {

    Integer findCount(PageHandler pageHandler, Map<String, Object> map);

    IPage<MonitorParams> findByPage(PageHandler pageHandler, Map<String, Object> map);

    MonitorParams getByCode(String paramCode);
}
