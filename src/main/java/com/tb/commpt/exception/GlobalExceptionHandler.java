package com.tb.commpt.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.global.SystemConfig;
import com.tb.commpt.model.comm.JsonResponse;
import com.tb.commpt.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常特殊处理，先于MySimpleMappingExceptionResolver
 */
//将作用在所有注解了@RequestMapping的控制器的方法上
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(GlobalExceptionHandler.class);
    private JsonResponse jsonResponse;

    private ModelAndView modelAndView;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    SystemConfig config;


    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExceptionHandler(RuntimeException ex, HttpServletRequest request, HttpServletResponse response) {
        modelAndView = new ModelAndView();
        jsonResponse = new JsonResponse();
        jsonResponse.setCode(ConsCommon.RUNTIME_EXCEPTION_CODE);
        jsonResponse.setMsg(ConsCommon.RUNTIME_EXCEPTION_MSG + ":" + ex.getMessage());
        if (ajaxReturn(request, response)) return null;
        modelAndView.addObject("jsonResponse", jsonResponse);
        modelAndView.setViewName("common/error");
        logger.error(jsonResponse.getMsg(), ex);
        return modelAndView;
    }

    //上传文件过大
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView maxUploadSizeExceededExceptionHandler(RuntimeException ex, HttpServletRequest request, HttpServletResponse response) {
        modelAndView = new ModelAndView();
        jsonResponse = new JsonResponse();
        jsonResponse.setCode(ConsCommon.MAXUPLOADSIZE_EXCEPTION_CODE);
        jsonResponse.setMsg(ConsCommon.MAXUPLOADSIZE_EXCEPTION_MSG + "(文件累计最大支持" + Integer.parseInt(config.FILE_MAXUPLOADSIZE) / (1024 * 1024) + "M)");
        jsonResponse.setDetailMsg(ex.getMessage());
        if (ajaxReturn(request, response)) return null;
        modelAndView.addObject("jsonResponse", jsonResponse);
        modelAndView.setViewName("common/error");
        logger.error(jsonResponse.getMsg(), ex);
        return modelAndView;
    }

    private boolean ajaxReturn(HttpServletRequest request, HttpServletResponse response) {
        if (CommonUtil.isAjaxRequest(request)) {
            try {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(objectMapper.writeValueAsString(jsonResponse));
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                logger.error("异常输出出错", e);
            }
            return true;
        }
        return false;
    }
}
