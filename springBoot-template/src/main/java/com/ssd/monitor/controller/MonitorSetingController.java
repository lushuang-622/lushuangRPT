package com.ssd.monitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.ssd.common.lang.ResultInfo;
import com.ssd.common.lang.Status;
import com.ssd.common.utils.LocalLogUtils;
import com.ssd.monitor.entity.MonitorParams;
import com.ssd.monitor.service.IMonitorModuleService;
import com.ssd.monitor.service.IMonitorParamsService;
import com.ssd.monitor.service.IMonitorPlateService;
import com.ssd.monitor.service.IMonitorSetingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author lus
 * @Date 2020/12/25 0025 13
 */
@Api(tags = "controller(/monitor/seting)", description = "提供系统模块功能配置参数管理相关的 Rest API")
@RestController
@RequestMapping("/monitor/seting")
public class MonitorSetingController {

    @Autowired
    IMonitorSetingService setingService;

    @Autowired
    private IMonitorPlateService plateService;

    @Autowired
    private IMonitorModuleService moduleService;

    @Autowired
    private IMonitorParamsService paramsService;

    @ApiOperation(value="设置系统模块功能的配置参数", notes="根据plateCode,moduleCode,paramCode设置paramValue。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "plateCode", value = "板块编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "moduleCode", value = "模块功能编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "paramCode", value = "配置参数标识编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "paramValue", value = "配置参数值", required = true, dataType = "String")
    })
    @PostMapping("/setMonitorModel")
    public Object setMonitorModel(@RequestParam(name = "plateCode") String plateCode,
                                  @RequestParam(name = "moduleCode") String moduleCode,
                                  @RequestParam(name = "paramCode") String paramCode,
                                  @RequestParam(name = "paramValue") String paramValue){
        Boolean state = true;
        String message = "";
        if(StringUtils.isEmpty(plateCode) || plateService.getByCode(plateCode) == null){
            state = false;
            message += "plateCode 不存在.";
        }
        if(StringUtils.isEmpty(moduleCode) || moduleService.getByCode(moduleCode) == null){
            state = false;
            message += "moduleCode 不存在.";
        }
        if(StringUtils.isEmpty(paramCode) || paramsService.getByCode(paramCode) == null){
            state = false;
            message += "paramCode 不存在.";
        }
        if(StringUtils.isEmpty(paramValue)){
            state = false;
            message += "paramValue 不存在.";
        }
        if(state){
            Map<String, Object> map = new HashMap<>();
            map.put("plateCode",plateCode);
            map.put("moduleCode",moduleCode);
            map.put("paramCode",paramCode);
            map.put("paramValue",paramValue);
            boolean b =  setingService.setMonitorModel(map);
            if(b){
                LocalLogUtils.info("setMonitorModel success");
                return new ResultInfo(Status.SUCCESS);
            }else{
                LocalLogUtils.info("setMonitorModel fail");
                return new ResultInfo(Status.WARN);
            }
        }else{
            LocalLogUtils.info("setMonitorModel fail");
            ResultInfo resultInfo = new ResultInfo(Status.WARN);
            resultInfo.message(message);
            return resultInfo;
        }
    }

    @ApiOperation(value="获取系统模块功能的配置参数", notes="根据plateCode,moduleCode,paramCode获取paramValue。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "plateCode", value = "板块编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "moduleCode", value = "模块功能编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "paramCode", value = "配置参数标识编码", required = true, dataType = "String")
    })
    @PostMapping("/getMonitorModel")
    public Object getMonitorModel(@RequestParam(name = "plateCode") String plateCode,
                                  @RequestParam(name = "moduleCode") String moduleCode,
                                  @RequestParam(name = "paramCode") String paramCode){
        Boolean state = true;
        String message = "";
        if(StringUtils.isEmpty(plateCode) || plateService.getByCode(plateCode) == null){
            state = false;
            message += "plateCode 不存在.";
        }
        if(StringUtils.isEmpty(moduleCode) || moduleService.getByCode(moduleCode) == null){
            state = false;
            message += "moduleCode 不存在.";
        }
        if(StringUtils.isEmpty(paramCode) || paramsService.getByCode(paramCode) == null){
            state = false;
            message += "paramCode 不存在.";
        }
        if(state){
            Map<String, Object> collomMap = new HashMap<>();
            //collomMap.put("plate_code",plateCode);
            collomMap.put("module_code",moduleCode);
            collomMap.put("param_code",paramCode);
            MonitorParams monitorParams =  setingService.getMonitorModel(collomMap);
            if(monitorParams != null){
                LocalLogUtils.info("getMonitorModel success");
                ResultInfo resultInfo = new ResultInfo(Status.SUCCESS);
                resultInfo.total(1);
                resultInfo.result(monitorParams);
                return resultInfo;
            }else{
                LocalLogUtils.info("getMonitorModel fail");
                return new ResultInfo(Status.WARN);
            }
        }else{
            LocalLogUtils.info("getMonitorModel fail");
            ResultInfo resultInfo = new ResultInfo(Status.WARN);
            resultInfo.message(message);
            return resultInfo;
        }

    }

    @ApiOperation(value="获取系统板块配置信息列表", notes="根据plateCode信息获取系统板块配置信息列表信息")
    @ApiImplicitParam(name = "plateCode", value = "板块编码", required = true, dataType = "String")
    @PostMapping("/getSetingByPlateCode")
    public Object getSetingByPlateCode(@RequestParam(name = "plateCode") String plateCode){
        Boolean state = true;
        String message = "";
        if(StringUtils.isEmpty(plateCode) || plateService.getByCode(plateCode) == null){
            state = false;
            message += "plateCode 不存在.";
        }
        if(state){
            Map<String, Object> map = new HashMap<>();
            map.put("plateCode",plateCode);
            JSONObject datas =  setingService.getSetingByPlateCode(map);
            if(datas != null){
                LocalLogUtils.info("getSetingByPlateCode success");
                ResultInfo resultInfo = new ResultInfo(Status.SUCCESS);
                resultInfo.total(1);
                resultInfo.result(datas);
                return resultInfo;
            }else{
                LocalLogUtils.info("getSetingByPlateCode fail");
                return new ResultInfo(Status.WARN);
            }
        }else{
            LocalLogUtils.info("getSetingByPlateCode fail");
            ResultInfo resultInfo = new ResultInfo(Status.WARN);
            resultInfo.message(message);
            return resultInfo;
        }
    }

}
