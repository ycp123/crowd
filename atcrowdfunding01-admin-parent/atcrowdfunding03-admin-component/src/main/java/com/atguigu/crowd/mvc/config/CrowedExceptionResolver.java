package com.atguigu.crowd.mvc.config;

import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//表示一个异常处理类
@ControllerAdvice
public class CrowedExceptionResolver {
    //指定异常类型
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName,exception,request,response);
    }

    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        //1.判断异常是否是ajax类型
        boolean judge = CrowdUtil.judgeRequestType(request);
        if (judge) {
            //2.将异常信息封装到ResultEntity中
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //3.将ResultEntity对象转化为json
            //3.1创建gson对象
            Gson gson = new Gson();
            //3.2转化
            String json = gson.toJson(resultEntity);
            //4.将json通过response传回
            response.getWriter().write(json);
            return null;
        }
        //5.如果不是ajax类型 则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        //6.将exception对象存入模型当中
        modelAndView.addObject("exception", exception);
        //7.设置对应视图名称
        modelAndView.setViewName(viewName);
        //8.返回ModelAndView对象
        return modelAndView;

    }
}
