package com.ssd.config.handler;

import com.ssd.common.lang.ResultInfo;
import com.ssd.common.lang.Status;
import com.ssd.config.DruidConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebResult;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author lus
 * @Date 2020/12/24 0024 15
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public Object globalException(HttpServletResponse response, NullPointerException ex){
        log.info("GlobalExceptionHandler...");
        log.info("错误代码："  + response.getStatus());
        ResultInfo result = new ResultInfo(Status.SYSTEM_ERROR);
        return result;
    }
}
