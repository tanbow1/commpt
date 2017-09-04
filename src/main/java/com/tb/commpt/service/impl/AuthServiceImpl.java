package com.tb.commpt.service.impl;

import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.mapper.XtJwtMapper;
import com.tb.commpt.mapper.XtUserMapper;
import com.tb.commpt.model.XtJwt;
import com.tb.commpt.model.XtUser;
import com.tb.commpt.service.IAuthService;
import com.tb.commpt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tanbo on 2017/9/4.
 */
@Service("authService")
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private JwtUtil jwt;

    @Autowired
    private XtUserMapper xtUserMapper;

    @Autowired
    private XtJwtMapper xtJwtMapper;

    /**
     * 校验用户信息
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public XtUser selectByUsernameAndPassword(String username, String password) {
        return xtUserMapper.selectByUsernameAndPassword(username, password);
    }

    /**
     * 新增token
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> saveJwt(String userId) throws Exception {
        xtJwtMapper.deleteByUserId(userId);
        String subject = JwtUtil.generalSubject(userId);
        String accessToken = jwt.createJWT(ConsCommon.JWT_ID, subject, ConsCommon.JWT_TTL);
        String refreshToken = jwt.createJWT(ConsCommon.JWT_ID, subject, ConsCommon.JWT_REFRESH_TTL);
        XtJwt xtJwt = new XtJwt();
        xtJwt.setUserId(userId);
        xtJwt.setAccessToken(accessToken);
        xtJwt.setRefreshToken(refreshToken);
        int insertCount = xtJwtMapper.insert2(xtJwt);
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("insertCount", String.valueOf(insertCount));
        resultMap.put("accessToken", accessToken);
        resultMap.put("refreshToken", refreshToken);
        return resultMap;
    }

    /**
     * 主动刷新token
     *
     * @param accessToken
     * @param refreshToken
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> refreshToken(String accessToken, String refreshToken) throws Exception {
        String userId = xtJwtMapper.selectByRefreshToken(accessToken, refreshToken);
        if (null == userId) {
            throw new BizLevelException("token失效，请重新登录");
        } else {
            return saveJwt(userId);
        }
    }


}
