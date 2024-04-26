package com.qf.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestOriginConfig implements RequestOriginParser {

    /**
     * 获取请求来源规则
     * @param request
     * @return
     */
    @Override
    public String parseOrigin(HttpServletRequest request) {
        String serverValue = request.getParameter("server");
        return serverValue;
    }
}
