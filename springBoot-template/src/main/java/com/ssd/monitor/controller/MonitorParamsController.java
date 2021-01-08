package com.ssd.monitor.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ssd.common.lang.PageHandler;
import com.ssd.common.lang.ResultInfo;
import com.ssd.common.lang.Status;
import com.ssd.common.utils.LocalLogUtils;
import com.ssd.monitor.entity.MonitorParams;
import com.ssd.monitor.service.IMonitorParamsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
@RestController
@RequestMapping("/monitor/monitorParams")
public class MonitorParamsController {


    @Autowired
    private IMonitorParamsService paramsService;

    /**
     * 增加修改
     */
    @PostMapping("/edit")
    public Object edit(MonitorParams entity){
        ResultInfo result = null;
        boolean save = false;
        if(entity.getId() == null){
            save = paramsService.save(entity);
            LocalLogUtils.info(entity.getClass()+"edit save success");
        }else{
            save = paramsService.updateById(entity);
            LocalLogUtils.info(entity.getClass()+"edit update success");
        }
        if(save){
            result = new ResultInfo(Status.SUCCESS);
        }else{
            result = new ResultInfo(Status.WARN);
            result.message("保存或者更新数据失败！");
        }
        return result;
    }
    /**
     * 删除
     */
    @PostMapping("/deleteById")
    public Object delete(MonitorParams entity){
        boolean b =  paramsService.removeById(entity.getId());
        if(b){
            LocalLogUtils.info(entity.getClass()+"deleteById delete success");
            return new ResultInfo(Status.SUCCESS);
        }else{
            LocalLogUtils.info(entity.getClass()+"deleteById delete fail");
            return new ResultInfo(Status.WARN);
        }

    }
    /**
     * 删除
     */
    @PostMapping("/deleteByIds")
    public Object delete(@RequestParam Map<String,Object> map){
        String ids = (String) map.get("ids");
        List<String> idsArr = Arrays.asList(ids.split(","));
        List<Integer> idList = new ArrayList<>();
        idsArr.forEach(s -> {
            idList.add(Integer.valueOf(s));
        });
        boolean b =  paramsService.removeByIds(idList);
        if(b){
            LocalLogUtils.info(ids+"deleteByIds delete success");
            return new ResultInfo(Status.SUCCESS);
        }else{
            LocalLogUtils.info(ids+"deleteByIds delete fail");
            return new ResultInfo(Status.WARN);
        }

    }
    /**
     * 根据id删除
     */
    @PostMapping("/findById")
    public Object findById(MonitorParams entity){
        ResultInfo result = null;
        if(entity.getId() == null){
            result = new ResultInfo(Status.WARN);
            result.message("缺失主键id");
        }else{
            MonitorParams obj = paramsService.getById(entity.getId());
            result = new ResultInfo(Status.SUCCESS);
            result.total(1);
            result.result(obj);
        }
        return result;
    }

    /**
     * 分页查询
     */
    @PostMapping("/findByPage")
    public Object findByPage(@RequestParam Map<String,Object> map){
        PageHandler pageHandler = new PageHandler(map);
        Integer total = paramsService.findCount(pageHandler,map);
        IPage<MonitorParams> datas = paramsService.findByPage(pageHandler,map);
        ResultInfo result = null;
        if(total > 0 ){
            result = new ResultInfo(Status.SUCCESS);
            result.total(1);
            result.result(datas);
        }else{
            result = new ResultInfo(Status.WARN);
            result.result("未查询到数据！");
        }
        return result;
    }

}

