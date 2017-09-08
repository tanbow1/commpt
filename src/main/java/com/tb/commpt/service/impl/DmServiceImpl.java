package com.tb.commpt.service.impl;

import com.tb.commpt.mapper.DmAccountMapper;
import com.tb.commpt.mapper.DmMenuMapper;
import com.tb.commpt.model.DmAccount;
import com.tb.commpt.model.DmMenu;
import com.tb.commpt.service.IDmService;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Tanbo on 2017/8/27.
 */

/***
 *
 */
@Service("dmService")
public class DmServiceImpl implements IDmService {

    @Resource
    private DmAccountMapper dmAccountMapper;

    @Resource
    private DmMenuMapper dmMenuMapper;

    @WebMethod
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

    @Override
    public List<DmMenu> selectMenuByPId(String parentId) {
        return dmMenuMapper.selectMenuByPId(parentId);
    }

    @Override
    public List<Map<String, Object>> mainMenus() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        getGns(dataList, "1");

        return null;
    }

    private void getGns(List<Map<String, Object>> dataList,
                        String parentId) {

        List<DmMenu> menuList = selectMenuByPId(parentId);
        DmMenu menu = null;
        Map<String, Object> map = null;

        Iterator menuIterator = menuList.iterator();

        while (menuIterator.hasNext()) {
            menu = (DmMenu) menuIterator.next();
            map = new ConcurrentHashMap<String, Object>();
            dataList.add(map);
            map.put("id", menu.getMenuId());
            map.put("text", menu.getMenuName());
            map.put("openType", menu.getOpenType());
            if (!StringUtils.isEmptyOrWhitespace(menu.getUrl())) {
                map.put("url", menu.getUrl());
            } else {
                map.put("url", "");
            }
            if ("1".equals(menu.getHaschildren())) {
                if ("0".equals(menu.getState())) {
                    map.put("state", "closed");
                }
                List<Map<String, Object>> childrenList = new ArrayList<Map<String, Object>>();
                map.put("children", childrenList);
                getGns(childrenList, menu.getParentId());
            }
        }
    }
}
