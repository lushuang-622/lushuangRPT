package com.ssd.monitor.dao;

import com.ssd.monitor.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
public interface IEmployeeDao extends BaseMapper<Employee> {

    Integer findCount();
}
