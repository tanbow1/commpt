package com.tb.commpt.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.commpt.constant.ConsCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异常handler
 *
 * @author Tanbo
 */
public class MySimpleMappingExceptionResolver implements
        HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory
            .getLogger(MySimpleMappingExceptionResolver.class);

    @SuppressWarnings("unchecked")
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object object, Exception ex) {
        Map<String, Object> tmpMap = null;
        logger.info(ex.getMessage());
        // 判断是否ajax请求
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request.getHeader(
                "X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            // 非ajax请求
            tmpMap = new ConcurrentHashMap<String, Object>();
            tmpMap.put("code", ConsCommon.ERROR_CODE);
            if (ex instanceof BizLevelException) {
                tmpMap.put("msg", "出现错误：" + ex.getMessage());
            } else {
                tmpMap.put("msg", "系统出错：" + ex.getMessage() + ",请联系系统管理员!");
            }
            return new ModelAndView("common/error", tmpMap);
        } else {
            // ajax请求，JSON格式返回
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                tmpMap = new ConcurrentHashMap<String, Object>();
                tmpMap.put("code", ConsCommon.ERROR_CODE);
                if (ex instanceof BizLevelException) {
                    tmpMap.put("msg", "出现错误：" + ex.getMessage());
                } else if (ex instanceof MaxUploadSizeExceededException) {
                    tmpMap.put("msg", "出现错误，文件过大!");//TODO 该异常待解决
                } else {
                    tmpMap.put("msg", "系统出错：" + ex.getMessage() + ",请联系系统管理员!");
                }

                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print(objectMapper.writeValueAsString(tmpMap));
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
