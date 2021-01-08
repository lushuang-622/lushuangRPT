package com.ssd.monitor.controller;


import com.ssd.common.lang.ResultInfo;
import com.ssd.common.lang.Status;
import com.ssd.monitor.entity.Employee;
import com.ssd.monitor.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  案例
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
@RestController
@RequestMapping("/monitor/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService empService;
    @PostMapping("/getUser")
    public Object getUser(){
        Employee emp = empService.getById(1);
        ResultInfo result = new ResultInfo(Status.SUCCESS);
        result.total(1);
        result.result(emp);
        return result;
    }

}

