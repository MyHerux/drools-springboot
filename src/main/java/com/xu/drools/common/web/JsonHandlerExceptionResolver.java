
package com.xu.drools.common.web;


import com.xu.drools.common.exception.BusinessException;
import com.xu.drools.common.exception.JsonResponse;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JsonHandlerExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LogManager.getLogger();

    @ResponseBody
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception exception) {
        ModelAndView mv = new ModelAndView();
        Map<String, String[]> parameterMap = request.getParameterMap();
        logException(handler, request, exception, parameterMap);
        JsonResponse jsonResponse = new JsonResponse(exception);
        JSONObject result = new JSONObject();
        result.put("code", jsonResponse.getCode());
        result.put("message", jsonResponse.getMessage());
        try {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            logger.error("json response error", exception);
            logger.error("error", e);
        }
        return mv;
    }

    private void logException(Object handler, HttpServletRequest request,
                              Exception exception, Map<String, String[]> parameterMap) {
        if (handler != null && HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            try {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Class<?> beanType = handlerMethod.getBeanType();
                String methodName = handlerMethod.getMethod().getName();
                RequestMapping controllerRequestMapping = beanType.getDeclaredAnnotation(RequestMapping.class);
                String classMapping = "";
                if (controllerRequestMapping != null) {
                    classMapping = controllerRequestMapping.value()[0];
                }
                RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                String methodMapping = "";
                if (methodRequestMapping != null) {
                    methodMapping = methodRequestMapping.value()[0];
                }
                if (!methodMapping.startsWith("/")) {
                    methodMapping = "/" + methodMapping;
                }
                String userInfo;
                try {
                    userInfo = request.getSession().getAttribute("AccountName").toString();
                } catch (Exception e) {
                    userInfo = "无授权回调";
                }
                Logger logger = LogManager.getLogger(beanType);
                if (exception instanceof BusinessException) {
                    int level = ((BusinessException) exception).getLevel();
                    if (level > 4) {
                        logger.error("User is:" + userInfo +
                                "RequestMapping is:" + classMapping + methodMapping +
                                " HandlerMethod is:" + beanType.getSimpleName() + "." + methodName + "()"
                                + " ParameterMap is:" + JSONObject.fromObject(parameterMap).toString());
                        logger.error(msg(exception));
                    } else {
                        logger.info("level:" + level);
                        logger.info("RequestMapping is:" + classMapping + methodMapping);
                        logger.info("HandlerMethod is:" + beanType.getSimpleName() + "." + methodName + "()");
                        logger.info("ParameterMap is:" + JSONObject.fromObject(parameterMap).toString());
                        logger.info("exception is:" + exception.getMessage());
                    }
                } else {
                    logger.error("User is:" + userInfo
                            + " RequestMapping is:" + classMapping + methodMapping
                            + " HandlerMethod is:" + beanType.getSimpleName() + "." + methodName + "()"
                            + " ParameterMap is:" + JSONObject.fromObject(parameterMap).toString()
                            + " Exception is:" + exception.toString());
                    logger.error(msg(exception));
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(handler + " execute failed.", exception);
                logger.error("error", e);
            }
        } else {
            logger.error(handler + " execute failed.", exception);
        }
    }

    private String msg(Exception e) {
        StringBuilder builder = new StringBuilder();
        StackTraceElement[] elements = e.getStackTrace();
        builder.append(e.getClass());
        builder.append(":");
        builder.append(e.getMessage());
        builder.append("\n");

        for (StackTraceElement element : elements) {
            String className = element.getClassName();
            if (className != null) {
                builder.append("\t");
                builder.append(element.getClassName());
                builder.append("#");
                builder.append(element.getMethodName());
                builder.append("(");
                builder.append(element.getFileName());
                builder.append(":");
                builder.append(element.getLineNumber());
                builder.append(")\n");
            }
        }

        return builder.toString();
    }

}
