package com.ssd.config.intercepors;

import com.ssd.common.utils.KeyManager;
import com.ssd.common.utils.RSAUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Key;
import java.util.Map;

/**
 * 账号鉴权拦截器
 *
 * @author lus
 * @Date 2020/12/24 0024 13
 */

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final static String access = "www.monitorServer.com";
    private final static String code = "8FCA79E9E34317C1E89C96424091CBE7";
    private final static String localUserCode = "monitor123";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //从请求参数中获取 会话的token与session中的token进行比对
        if(request.getHeader("access") != null && access.equals(request.getHeader("access"))){
            String accessToken = request.getHeader("accessToken");
            if(StringUtils.isNotEmpty(accessToken)){
                //暂时使用userCode代替用户
                String userCode = request.getHeader("userCode");
                if(localUserCode.equals(userCode)){
                    Map<String, Key> keyMap = KeyManager.getKey(userCode);
                    String timeCode = RSAUtils.rsaDecrypt(accessToken, RSAUtils.getPrivateKey(keyMap), RSAUtils.CHARSET);
                    //将timeCode进行解析
                    if(code.equals(timeCode)){
                        return true;
                    }
                }

            }
        }
        //response.sendRedirect(request.getContextPath() + "/error");
        request.getRequestDispatcher("/error").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
