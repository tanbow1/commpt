package com.tb.commpt.service;

import java.util.Map;

/**
 * Created by Tanbo on 2017/9/4.
 */
public interface IAuthService {

    Map<String, String> saveJwt(String userId) throws Exception;

    Map<String, String> refreshToken(String accessToken, String refreshToken) throws Exception;

    Map<String, String> selectByAccessToken(String accessToken);

    Map<String, String> checkToken(String accessToken, String refreshToken) throws Exception;

}
