package com.tb.commpt.service.impl;

import com.tb.commpt.mapper.XtJwtMapper;
import com.tb.commpt.mapper.XtUserMapper;
import com.tb.commpt.model.XtJwt;
import com.tb.commpt.model.XtUser;
import com.tb.commpt.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Tanbo on 2017/9/4.
 */
@Service("authService")
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private XtUserMapper xtUserMapper;

    @Autowired
    private XtJwtMapper xtJwtMapper;

    @Override
    public XtUser selectByUsernameAndPassword(String username, String password) {
        return xtUserMapper.selectByUsernameAndPassword(username, password);
    }

    @Override
    public int saveJwt(XtJwt xtJwt) {
        xtJwtMapper.deleteByUserId(xtJwt.getUserId());
        return xtJwtMapper.insert2(xtJwt);
    }


}
