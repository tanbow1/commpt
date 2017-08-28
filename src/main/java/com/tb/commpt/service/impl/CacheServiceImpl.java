package com.tb.commpt.service.impl;

import com.tb.commpt.dao.ICacheDao;
import com.tb.commpt.service.ICacheService;
import org.springframework.stereotype.Service;

@Service("cacheService")
public class CacheServiceImpl implements ICacheService {

    private ICacheDao cacheDao;

}
