package com.tb.commpt.service.impl;

import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.mapper.XtUserMapper;
import com.tb.commpt.model.XtUser;
import com.tb.commpt.model.XtUserAccount;
import com.tb.commpt.model.XtUserAddress;
import com.tb.commpt.model.XtUserRole;
import com.tb.commpt.service.IUserService;
import com.tb.commpt.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tanbo on 2017/9/4.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private XtUserMapper xtUserMapper;

    /**
     * 用户名、密码获取用户信息
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public XtUser selectByUsernameAndPassword(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        XtUser xtUser = xtUserMapper.selectExistsUser(username);
        if (null != xtUser) {
            if (MD5Util.validateStr(password, xtUser.getPassEnc())) {
                return xtUser;
            }
        }
        return null;
    }

    /**
     * 保存用户信息
     *
     * @param user
     * @param userAccount
     * @param userAddress
     * @param userRole
     * @return 用户主键
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Override
    public String saveUserInfo(XtUser user, XtUserAccount userAccount, XtUserAddress userAddress, XtUserRole userRole) throws UnsupportedEncodingException, NoSuchAlgorithmException, BizLevelException {
        if (null != user.getPass()) {
            user.setPassEnc(MD5Util.getEncryptedStr(user.getPass()));
            user.setPass("");
        }
        XtUser existsUser = xtUserMapper.selectExistsUser(user.getUserAccount());
        if (null != existsUser) {
            throw new BizLevelException(ConsCommon.WARN_MSG_001);
        }
        existsUser = xtUserMapper.selectExistsUser(user.getCardNumber());
        if (null != existsUser) {
            throw new BizLevelException(ConsCommon.WARN_MSG_002);
        }
        existsUser = xtUserMapper.selectExistsUser(user.getMobile());
        if (null != existsUser) {
            throw new BizLevelException(ConsCommon.WARN_MSG_003);
        }

        int insertCount = xtUserMapper.insert2(user);
        if (insertCount > 0)
            return user.getUserId();
        return null;
    }

    @Override
    public XtUser selectByPrimaryKey(String userId) {
        return xtUserMapper.selectByPrimaryKey(userId);
    }
}
