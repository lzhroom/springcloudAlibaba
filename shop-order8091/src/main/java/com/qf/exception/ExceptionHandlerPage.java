package com.qf.exception;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
public class ExceptionHandlerPage implements UrlBlockHandler {

    /**
     *
     * @param request
     * @param response
     * @param e BlockException Sentinel提供了描述所有异常的父类，它包含了很多子异常
     * @throws IOException
     */
    @Override
    public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException e) throws IOException {
        //向浏览器响应数据
        response.setContentType("application/json;charset=utf-8");
        ResponseData data = null;
        if (e instanceof FlowException){
            //QPS  线程数触发的限流
            data = new ResponseData(100,"这是一个普通的流控规则触发的异常");
        }
        if(e instanceof ParamFlowException){
            //参数限流异常
            data = new ResponseData(101,"这是一个参数限流规则触发的异常");
        }
        if(e instanceof DegradeException){
            //降级异常
            data = new ResponseData(102,"这是一个降级规则触发的异常");
        }
        if(e instanceof AuthorityException){
            //授权异常
            data = new ResponseData(101,"这是一个授权规则触发的异常");
        }
        if(e instanceof SystemBlockException){
            //系统负载异常
            data = new ResponseData(101,"这是一个系统负载规则触发的异常");
        }
        response.getWriter().write(JSON.toJSONString(data));
    }
}
