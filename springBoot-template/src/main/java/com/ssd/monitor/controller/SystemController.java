package com.ssd.monitor.controller;

import com.ssd.common.lang.ResultInfo;
import com.ssd.common.lang.Status;
import com.ssd.common.utils.KeyManager;
import com.ssd.common.utils.RSAUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Map;

/**
 * @author lus
 * @Date 2020/12/24 0024 14
 */
@Api(tags = "controller(/monitor)", description = "提供系统登录相关的 Rest API")
@RestController
@RequestMapping(value="/monitor")
public class SystemController {

    private final static String localUserCode = "monitor123";

    @ApiOperation(value="登录获取动态令牌", notes="根据userCode信息获取用户会话令牌信息")
    @ApiImplicitParam(name = "userCode", value = "用户身份标识编码", required = true, dataType = "String")
    @PostMapping("/getToken")
    public Object getToken(@RequestParam(name = "userCode") String userCode, HttpServletResponse response) {
        if (localUserCode.equals(userCode)) {
            Map<String, Key> initKey = KeyManager.getKey(userCode);
            if(initKey == null){
                initKey = RSAUtils.initKey();
                KeyManager.setKey(userCode, initKey);
            }
            String publicKey = RSAUtils.getPublicKey(initKey);
            response.addHeader("publicKey", publicKey);
            ResultInfo result = new ResultInfo(Status.SUCCESS);
            result.total(1);
            result.result(publicKey);
            return result;
        } else {
            ResultInfo result = new ResultInfo(Status.LOGIN_USER_NOT_EXIST);
            result.total(1);
            result.result(userCode);
            return result;
        }

    }

    @PostMapping("/error")
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo result = new ResultInfo(Status.INSUFFICIENT_PERMISSION);
        return result;
    }

}
