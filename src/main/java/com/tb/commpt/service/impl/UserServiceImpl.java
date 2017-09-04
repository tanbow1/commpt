package com.tb.commpt.service.impl;

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
     * 获取用户信息
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public XtUser selectByUsernameAndPassword(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return xtUserMapper.selectByUsernameAndPassword(username, MD5Util.getEncryptedStr(password));
    }

    @Override
    public String saveUserInfo(XtUser user, XtUserAccount userAccount, XtUserAddress userAddress, XtUserRole userRole) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (null != user.getPass()) {
            user.setPassEnc(MD5Util.getEncryptedStr(user.getPass()));
            user.setPass("");
        }
        return xtUserMapper.insert2(user);
    }
}
