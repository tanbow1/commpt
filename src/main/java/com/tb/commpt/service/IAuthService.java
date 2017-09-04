package com.tb.commpt.service;

import com.tb.commpt.model.XtJwt;
import com.tb.commpt.model.XtUser;

/**
 * Created by Tanbo on 2017/9/4.
 */
public interface IAuthService {

    XtUser selectByUsernameAndPassword(String username, String password);

    int saveJwt(XtJwt xtJwt);

}
