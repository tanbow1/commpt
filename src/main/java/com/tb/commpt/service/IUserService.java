package com.tb.commpt.service;

import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.model.XtUser;
import com.tb.commpt.model.XtUserAccount;
import com.tb.commpt.model.XtUserAddress;
import com.tb.commpt.model.XtUserRole;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tanbo on 2017/9/4.
 */
public interface IUserService {

    XtUser selectByUsernameAndPassword(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    String saveUserInfo(XtUser user, XtUserAccount userAccount, XtUserAddress userAddress, XtUserRole userRole) throws UnsupportedEncodingException, NoSuchAlgorithmException, BizLevelException;

    XtUser selectByPrimaryKey(String userId);
}
