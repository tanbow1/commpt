package com.tb.commpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.model.XtJwt;
import com.tb.commpt.model.XtUser;

import java.util.Map;

/**
 * Created by Tanbo on 2017/9/4.
 */
public interface IAuthService {

    Map<String, String> saveJwt(String userId) throws Exception;

    Map<String, String> refreshToken(String accessToken, String refreshToken) throws Exception;

    Map<String, String> selectByAccessToken(String accessToken);

}
