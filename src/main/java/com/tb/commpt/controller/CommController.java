package com.tb.commpt.controller;

import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.exception.SystemLevelException;
import com.tb.commpt.global.SpringContext;
import com.tb.commpt.global.SystemConfig;
import com.tb.commpt.model.JsonRequest;
import com.tb.commpt.model.JsonResponse;
import com.tb.commpt.model.XtUser;
import com.tb.commpt.service.IAuthService;
import com.tb.commpt.service.IDmService;
import com.tb.commpt.service.IUserService;
import com.tb.commpt.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tanbo on 2017/8/22.
 */
@Controller
@RequestMapping("/comm")
public class CommController {
    private static Logger logger = LoggerFactory.getLogger(CommController.class);

    @Autowired
    private SystemConfig config;

    @Autowired
    private IAuthService authService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDmService dmService;

    /**
     * 统一页面跳转
     *
     * @param page
     * @return
     */
    @RequestMapping("/toPage/{page}")
    public ModelAndView toPage(@PathVariable String page) {
        return new ModelAndView(page);
    }

    @RequestMapping("/error")
    public ModelAndView error() throws BizLevelException {
        return new ModelAndView("common/error");
    }


    /**
     * 登录
     *
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/login")
    public JsonResponse login(@ModelAttribute JsonRequest jsonRequest) throws Exception {
        JsonResponse jsonResponse = new JsonResponse();

        XtUser user = userService.selectByUsernameAndPassword(String.valueOf(jsonRequest.getReqData().get("username")),
                String.valueOf(jsonRequest.getReqData().get("password")));

        if (null != user) {

            Map<String, String> resultMap = authService.saveJwt(user.getUserId());
            if ("0".equals(resultMap.get("insertCount"))) {
                throw new SystemLevelException(ConsCommon.ERROR_MSG_UNKNOW + ":插入token失败");
            }

            jsonResponse.getRepData().put(ConsCommon.ACCESS_TOKEN, resultMap.get("accessToken"));
            jsonResponse.getRepData().put(ConsCommon.REFRESH_TOKEN, resultMap.get("refreshToken"));

        } else {
            jsonResponse.setCode(ConsCommon.ERROR_CODE);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_007);
        }

        return jsonResponse;
    }

    /**
     * 刷新token
     *
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/refreshToken")
    public JsonResponse refreshToken(@ModelAttribute JsonRequest jsonRequest) throws Exception {
        JsonResponse jsonResponse = new JsonResponse();
        String accessToken = jsonRequest.getAccessToken();
        String refreshToken = jsonRequest.getRefreshToken();
        Map<String, String> resultMap = authService.refreshToken(accessToken, refreshToken);
        if ("0".equals(resultMap.get("insertCount"))) {
            throw new SystemLevelException(ConsCommon.ERROR_MSG_UNKNOW + ":刷新token失败");
        }
        jsonResponse.getRepData().put("resultData", resultMap);
        return jsonResponse;
    }

    /**
     * 校验token
     *
     * @param jsonRequest
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/checkToken")
    public JsonResponse checkToken(@ModelAttribute JsonRequest jsonRequest) throws Exception {
        JsonResponse jsonResponse = new JsonResponse();
        String accessToken = jsonRequest.getAccessToken();
        String refreshToken = jsonRequest.getRefreshToken();
        Map<String, String> resultMap = authService.checkToken(accessToken, refreshToken);
        if (null == resultMap) {
            throw new BizLevelException(ConsCommon.WARN_MSG_006);
        }
        jsonResponse.getRepData().put("resultData", resultMap);
        return jsonResponse;
    }

    /**
     * 根据父节点获取主菜单
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMaintree")
    public List getMaintree() {
        return dmService.getMenuTree("1");
    }

    /**
     * 统一ajax调用
     * <p>
     * 目前支持的method参数类型：int boolean string
     *
     * @param jsonRequest
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws BizLevelException
     */
    @ResponseBody
    @RequestMapping("/getJsonData")
    public JsonResponse getJsonData(@ModelAttribute JsonRequest jsonRequest,
                                    HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse) throws BizLevelException, InvocationTargetException {
        JsonResponse jsonResponse = new JsonResponse();
        String serviceName = jsonRequest.getServiceName();
        String methodName = jsonRequest.getMethodName();
        List<ConcurrentHashMap<String, Object>> methodParams = jsonRequest.getMethodParams();

        Object serviceBean = SpringContext.getBean(serviceName);
        try {
            Class[] paramClasses = new Class[]{};
            Object[] paramValues = new Object[]{};
            if (null != methodParams && methodParams.size() > 0) {
                paramClasses = new Class[methodParams.size()];
                paramValues = new Object[methodParams.size()];
                Map<String, Object> tempParamMap;
                int index;
                String type;
                for (int i = 0; i < methodParams.size(); i++) {
                    tempParamMap = methodParams.get(i);
                    index = Integer.parseInt(String.valueOf(tempParamMap.get("index"))) - 1;
                    type = String.valueOf(tempParamMap.get("type")).toLowerCase();
                    paramClasses[index]
                            = CommonUtil.getClassName(type);
                    if ("int".equals(type)) {
                        paramValues[index] = Integer.parseInt(String.valueOf(tempParamMap.get("value")));
                    } else if ("string".equals(type)) {
                        paramValues[index] = String.valueOf(tempParamMap.get("value"));
                    } else if ("boolean".equals(type)) {
                        paramValues[index] = Boolean.valueOf(String.valueOf(tempParamMap.get("value")));
                    } else {
                        throw new BizLevelException(ConsCommon.WARN_MSG_015);
                    }
                }
            }

            Method method = serviceBean.getClass().getMethod(methodName, paramClasses);
            jsonResponse = (JsonResponse) method.invoke(serviceBean, paramValues);

        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_008);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_008);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_009);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_009);
        } catch (ClassCastException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_012);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_012);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_013);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_013);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_014);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_014);
        }

        return jsonResponse;
    }


    /**
     * 统一ajax调用
     * <p>
     * 默认service入参：JsonRequest，出参：JsonResponse
     *
     * @param jsonRequest
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws InvocationTargetException 该异常需要抛出给异常类统一处理，否则无法明确service中抛出的具体错误原因
     */
    @ResponseBody
    @RequestMapping("/getJsonData2")
    public JsonResponse getJsonData2(@ModelAttribute JsonRequest jsonRequest,
                                     HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse) throws InvocationTargetException {
        JsonResponse jsonResponse = new JsonResponse();
        String serviceName = jsonRequest.getServiceName();
        String methodName = jsonRequest.getMethodName();
        Object serviceBean = SpringContext.getBean(serviceName);
        try {
            Method method = serviceBean.getClass().getMethod(methodName, JsonRequest.class);
            jsonResponse = (JsonResponse) method.invoke(serviceBean, jsonRequest);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_008);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_008);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_009);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_009);
        } catch (ClassCastException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_012);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_012);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            jsonResponse.setCode(ConsCommon.WARN_CODE_014);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_014);
        }

        return jsonResponse;
    }


    @ResponseBody
    @RequestMapping(value = "/uploadFiles", method = {RequestMethod.POST})
    public JsonResponse uploadFjs(@RequestParam(value = "uploadFiles", required = false) MultipartFile[] files,
                                  HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse) {
        JsonResponse jsonResponse = new JsonResponse();
        int fileMaxlength = config.FILE_MAXLENGTH;


        return jsonResponse;
    }

}

