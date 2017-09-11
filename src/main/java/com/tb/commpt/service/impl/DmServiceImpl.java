package com.tb.commpt.service.impl;

import com.tb.commpt.mapper.DmAccountMapper;
import com.tb.commpt.mapper.DmGjdqMapper;
import com.tb.commpt.mapper.DmMenuMapper;
import com.tb.commpt.model.DmAccount;
import com.tb.commpt.model.DmGjdq;
import com.tb.commpt.model.DmMenu;
import com.tb.commpt.model.JsonResponse;
import com.tb.commpt.service.IDmService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private DmAccountMapper dmAccountMapper;

    @Autowired
    private DmMenuMapper dmMenuMapper;

    @Autowired
    private DmGjdqMapper dmGjdqMapper;

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

    /**
     * 根据父节点获取菜单树
     */
    @Override
    public List<Map<String, Object>> getMenuTree(String parentId) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        getGns(dataList, parentId);
        return dataList;
    }

    /**
     * 根据父节点迭代菜单树
     *
     * @param dataList
     * @param parentId
     */
    private void getGns(List<Map<String, Object>> dataList,
                        String parentId) {
        List<DmMenu> menuList = dmMenuMapper.selectMenuByPId(parentId);
        DmMenu menu = null;
        Map<String, Object> map = null;

        Iterator menuIterator = menuList.iterator();
        while (menuIterator.hasNext()) {
            map = new ConcurrentHashMap<String, Object>();
            menu = (DmMenu) menuIterator.next();
            dataList.add(map);
            map.put("id", menu.getMenuId());
            map.put("text", menu.getMenuName());
            map.put("openType", menu.getOpenType());
            map.put("readonly", menu.getReadonly());
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
                getGns(childrenList, menu.getMenuId());
            }
        }
    }


    /**
     * 获取国籍地区列表（分页）
     *
     * @param pageStart
     * @param pageEnd
     * @return
     */
    @Override
    public JsonResponse getGjdqListPagination(int pageStart, int pageEnd) {
        JsonResponse jsonResponse = new JsonResponse();
        List<DmGjdq> dmGjdqList = dmGjdqMapper.selectGjdqList(pageStart, pageEnd);
        if (null != dmGjdqList)
            jsonResponse.getRepData().put("gjdqList", dmGjdqList);
        return jsonResponse;
    }
}
