package com.ssd.monitor.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssd.monitor.entity.Employee;
import com.ssd.monitor.dao.IEmployeeDao;
import com.ssd.monitor.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class EmployeeServiceImpl extends ServiceImpl<IEmployeeDao, Employee> implements IEmployeeService {

    IEmployeeDao employeeDao;

    @Override
    public Integer findCount() {
        return employeeDao.findCount();
    }

    @Override
    public IPage<Employee> findByPage() {
        Page<Employee> page = new Page<>(0, 2);
        QueryWrapper<Employee> query = new QueryWrapper<>();
        query.orderByDesc("name");
        IPage<Employee> pageResult = employeeDao.selectPage(page, query);
        return pageResult;
    }
}
