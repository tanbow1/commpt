package com.tb.commpt.service.impl;

import com.tb.commpt.mapper.DmAccountMapper;
import com.tb.commpt.model.DmAccount;
import com.tb.commpt.service.BaseService;
import com.tb.commpt.service.IDmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by Tanbo on 2017/8/27.
 */
@WebService(endpointInterface = "com.tb.commpt.service.IDmService")
@Service("dmService")
public class DmServiceImpl implements IDmService {

    @Resource
    private DmAccountMapper dmAccountMapper;


    @Override
    public List<DmAccount> selectAllDmAccount() {
        return dmAccountMapper.selectAll();
    }

    @Override
    public DmAccount selectDmAccountByPrimaryKey(String accountType) {
        return dmAccountMapper.selectByPrimaryKey(accountType);
    }

    @Override
    public int deleteDmAccountByPrimaryKey(String accountType) {
        return dmAccountMapper.deleteByPrimaryKey(accountType);
    }

    @Override
    public int saveDmAccount(DmAccount record) {
        return dmAccountMapper.insert(record);
    }
}
