package com.ssd.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ssd.monitor.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
public interface IEmployeeService extends IService<Employee> {

    public Integer findCount();

    public IPage<Employee> findByPage();

}
