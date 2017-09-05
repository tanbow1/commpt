package com.tb.commpt.controller;

import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.exception.SystemLevelException;
import com.tb.commpt.model.JsonRequest;
import com.tb.commpt.model.JsonResponse;
import com.tb.commpt.model.XtJwt;
import com.tb.commpt.model.XtUser;
import com.tb.commpt.service.IAuthService;
import com.tb.commpt.service.IUserService;
import com.tb.commpt.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanbo on 2017/8/22.
 */
@Controller
@RequestMapping("/comm")
public class CommViewController {
    private static Logger logger = LoggerFactory.getLogger(CommViewController.class);

    @Autowired
    private IAuthService authService;

    @Autowired
    private IUserService userService;

    @RequestMapping("/tologin")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @ResponseBody
    @RequestMapping("/login")
    public JsonResponse login(@ModelAttribute JsonRequest jsonRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        JsonResponse jsonResponse = new JsonResponse();

        XtUser user = userService.selectByUsernameAndPassword(String.valueOf(jsonRequest.getReqData().get("username")),
                String.valueOf(jsonRequest.getReqData().get("password")));

        if (null != user) {

            Map<String, String> resultMap = authService.saveJwt(user.getUserId());
            if ("0".equals(resultMap.get("insertCount"))) {
                throw new SystemLevelException(ConsCommon.ERROR_CODE_UNKNOW + ":插入token失败");
            }

            jsonResponse.getRepData().put(ConsCommon.ACCESS_TOKEN, resultMap.get("accessToken"));
            jsonResponse.getRepData().put(ConsCommon.REFRESH_TOKEN, resultMap.get("refreshToken"));

        } else {
            jsonResponse.setCode(ConsCommon.ERROR_CODE);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_007);
        }

        return jsonResponse;
    }

    @RequestMapping("/index")
    public ModelAndView index() throws UnsupportedEncodingException, NoSuchAlgorithmException, BizLevelException {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("data", "hahahhh弹拨");
        ModelAndView mv = new ModelAndView("index");
        // return new ModelAndView("index",modelMap);

        mv.addObject("data2", "谈波");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/refreshToken")
    public JsonResponse refreshToken(@ModelAttribute JsonRequest jsonRequest) throws Exception {
        JsonResponse jsonResponse = new JsonResponse();
        String accessToken = jsonRequest.getAccessToken();
        String refreshToken = jsonRequest.getRefreshToken();
        Map<String, String> resultMap = authService.refreshToken(accessToken, refreshToken);
        jsonResponse.getRepData().put("resultData", resultMap);
        return jsonResponse;
    }

    @RequestMapping("/error")
    public ModelAndView error() throws BizLevelException {
        throw new BizLevelException("出错了，呵呵呵！");
        //return new ModelAndView("common/error");
    }
}
