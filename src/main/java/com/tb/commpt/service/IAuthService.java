package com.tb.commpt.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tanbo on 2017/9/4.
 */
public interface IAuthService {

    ConcurrentHashMap<String, String> saveJwt(String userId) throws Exception;

    ConcurrentHashMap<String, String> refreshToken(String accessToken, String refreshToken) throws Exception;

    ConcurrentHashMap<String, String> selectByAccessToken(String accessToken);

    ConcurrentHashMap<String, String> checkToken(String accessToken, String refreshToken) throws Exception;

}
