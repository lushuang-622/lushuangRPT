package com.ssd.monitor.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ssd.common.lang.PageHandler;
import com.ssd.common.lang.ResultInfo;
import com.ssd.common.lang.Status;
import com.ssd.common.utils.LocalLogUtils;
import com.ssd.monitor.entity.MonitorPlate;
import com.ssd.monitor.service.IMonitorPlateService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
@Api(tags = "controller(/monitor/monitorPlate)", description = "提供板块管理相关的 Rest API")
@RestController
@RequestMapping("/monitor/monitorPlate")
public class MonitorPlateController {

    @Autowired
    private IMonitorPlateService plateService;

    /**
     * 增加修改
     */
    @ApiOperation(value = "新增或修改板块信息", notes = "根据MonitorPlate对象实体属性修改板块信息")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键：新增时为空", required = false, dataType = "Long", example="1"),
            @ApiImplicitParam(name = "palteName", value = "板块名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "palteCode", value = "板块编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "areaCode", value = "地区编码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "paltePath", value = "板块页面地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "enable", value = "是否启用（1：是，0：否）", required = true, dataType = "Integer", example="1")
    })*/
    @PostMapping("/edit")
    public Object edit(MonitorPlate entity) {
        ResultInfo result = null;
        boolean save = false;
        if (entity.getId() == null) {
            save = plateService.save(entity);
            LocalLogUtils.info(entity.getClass() + "edit save success");
        } else {
            save = plateService.updateById(entity);
            LocalLogUtils.info(entity.getClass() + "edit update success");
        }
        if (save) {
            result = new ResultInfo(Status.SUCCESS);
        } else {
            result = new ResultInfo(Status.WARN);
            result.message("保存或者更新数据失败！");
        }
        return result;
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除板块信息", notes = "根据MonitorPlate对象实体的id属性删除板块信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "Long", example="1")
    @PostMapping("/deleteById")
    public Object delete(@RequestParam(name = "id") Long id) {
        boolean b = plateService.removeById(id);
        if (b) {
            LocalLogUtils.info("MonitorPlate对象" + id + "deleteById delete success");
            return new ResultInfo(Status.SUCCESS);
        } else {
            LocalLogUtils.info("MonitorPlate对象" + id + "deleteById delete fail");
            return new ResultInfo(Status.WARN);
        }

    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除板块信息", notes = "根据逗号分隔的id字符串删除板块信息")
    @ApiImplicitParam(name = "ids", value = "逗号分隔的id字符串", required = true, dataType = "String")
    @PostMapping("/deleteByIds")
    public Object delete(@RequestParam(name = "ids") String ids) {
        List<String> idsArr = Arrays.asList(ids.split(","));
        List<Integer> idList = new ArrayList<>();
        idsArr.forEach(s -> {
            idList.add(Integer.valueOf(s));
        });
        boolean b = plateService.removeByIds(idList);
        if (b) {
            LocalLogUtils.info(ids + "deleteByIds delete success");
            return new ResultInfo(Status.SUCCESS);
        } else {
            LocalLogUtils.info(ids + "deleteByIds delete fail");
            return new ResultInfo(Status.WARN);
        }

    }

    /**
     * 根据id删除
     */
    @ApiOperation(value = "查询板块信息", notes = "根据id查询板块信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "Long", example="1")
    @PostMapping("/findById")
    public Object findById(@RequestParam(name = "id") Long id) {
        ResultInfo result = null;
        if (id == null) {
            result = new ResultInfo(Status.WARN);
            result.message("缺失主键id");
        } else {
            MonitorPlate obj = plateService.getById(id);
            result = new ResultInfo(Status.SUCCESS);
            result.total(1);
            result.result(obj);
        }
        return result;
    }

    /**
     * 分页查询
     */
    @ApiOperation(value = "分页查询板块信息", notes = "分页查询板块信息\n" +
            "limit 页面 （0~100...） 默认 0\n" +
            "size 没有条数 默认10\n" +
            "sortName 排序查询的字段 默认 id\n" +
            "sortOrder 排序的顺序类型 （desc/asc） 默认 desc" +
            "以“L_”开头的字段封装到 likeMap中执行模糊查询\n" +
            "以“lt_”开头的字段表示匹配 “<”,类型为Long类型。\n" +
            "以“lteq_”开头的字段表示匹配 “<=”,类型为Long类型。\n" +
            "以“gt_”开头的字段表示匹配 “>”,类型为Long类型。\n" +
            "以“gteq_”开头的字段表示匹配 “<=”,类型为Long类型。\n" +
            "以“st_”开头的字段表示匹配 “start_time”,类型为date类型,格式为 “yyyyMMdd-HH:mm:ss”。\n" +
            "以“et_”开头的字段表示匹配 “end_time”,类型为date类型,格式为 “yyyyMMdd-HH:mm:ss”。\n" +
            "以“ct_”开头的字段表示匹配 “create_time”,类型为date类型,格式为 “yyyyMMdd-HH:mm:ss”。\n" +
            "以“ut_”开头的字段表示匹配 “update_time”,类型为date类型,格式为 “yyyyMMdd-HH:mm:ss”。\n" +
            "剩余可以传递的属于MonitorPlate实体的数据封装到querryMap中执行完全匹配查询"
    )
    @ApiImplicitParam(name = "map", value = "接收查询条件的集合", required = false, dataType = "Object")
    @PostMapping("/findByPage")
    public Object findByPage(@RequestParam Map<String, Object> map) {
        PageHandler pageHandler = new PageHandler(map);
        Integer total = plateService.findCount(pageHandler, map);
        IPage<MonitorPlate> datas = plateService.findByPage(pageHandler, map);
        ResultInfo result = null;
        if (total > 0) {
            result = new ResultInfo(Status.SUCCESS);
            result.total(total);
            result.result(datas);
        } else {
            result = new ResultInfo(Status.WARN);
            result.result("未查询到数据！");
        }
        return result;
    }
}

