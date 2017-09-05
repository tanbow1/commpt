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
import org.thymeleaf.util.StringUtils;

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
    private XtJwtMapper xtJwtMapper;


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
        if (StringUtils.isEmptyOrWhitespace(accessToken)) {
            throw new BizLevelException(ConsCommon.WARN_MSG_004);
        }
        if (StringUtils.isEmptyOrWhitespace(refreshToken)) {
            throw new BizLevelException(ConsCommon.WARN_MSG_005);
        }
        Map<String, String> reMap = xtJwtMapper.selectByAccessToken(accessToken);
        String userId = xtJwtMapper.selectByRefreshToken(accessToken, refreshToken);
        if (null == userId) {
            throw new BizLevelException(ConsCommon.WARN_MSG_006);
        } else {
            return saveJwt(userId);
        }
    }

    @Override
    public Map<String, String> selectByAccessToken(String accessToken) {
        return xtJwtMapper.selectByAccessToken(accessToken);
    }


}
