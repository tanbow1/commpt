package com.tb.commpt.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.mapper.DmAccountMapper;
import com.tb.commpt.mapper.DmGjdqMapper;
import com.tb.commpt.mapper.DmMenuMapper;
import com.tb.commpt.model.*;
import com.tb.commpt.service.IDmService;
import com.tb.commpt.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
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
@WebService
@Service("dmService")
public class DmServiceImpl implements IDmService {

    @Autowired
    private DmAccountMapper dmAccountMapper;

    @Autowired
    private DmMenuMapper dmMenuMapper;

    @Autowired
    private DmGjdqMapper dmGjdqMapper;

    ObjectMapper objectMapper = new ObjectMapper();

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
    public List<ConcurrentHashMap<String, Object>> getMenuTree(String parentId) {
        List<ConcurrentHashMap<String, Object>> dataList = new ArrayList<>();
        getGns(dataList, parentId);
        return dataList;
    }

    /**
     * 迭代菜单树
     *
     * @param dataList
     * @param parentId
     */
    private void getGns(List<ConcurrentHashMap<String, Object>> dataList,
                        String parentId) {
        List<DmMenu> menuList = dmMenuMapper.selectMenuByPId(parentId);
        DmMenu menu = null;
        ConcurrentHashMap<String, Object> map = null;

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
                List<ConcurrentHashMap<String, Object>> childrenList = new ArrayList<ConcurrentHashMap<String, Object>>();
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

    /**
     * 批量删除
     *
     * @param jsonRequest
     * @return
     * @throws IOException
     */
    @Override
    public JsonResponse deleteGjdqBatch(JsonRequest jsonRequest) throws IOException {
        JsonResponse jsonResponse = new JsonResponse();
        JavaType javaType = CommonUtil.getCollectionType(ArrayList.class, DmGjdq.class);
        List list = objectMapper.readValue(String.valueOf(jsonRequest.getReqData().get("records")), javaType);
        Iterator it = list.iterator();
        DmGjdq dmGjdq;
        int deleteCount;
        List<DmGjdq> successList = new ArrayList<DmGjdq>();
        List<DmGjdq> errorList = new ArrayList<DmGjdq>();
        while (it.hasNext()) {
            dmGjdq = (DmGjdq) it.next();
            deleteCount = dmGjdqMapper.deleteByPrimaryKey(dmGjdq.getUuid());
            if (deleteCount > 0) {
                successList.add(dmGjdq);
            } else {
                errorList.add(dmGjdq);
                jsonResponse.setMsg(ConsCommon.WARN_MSG_017);
            }
        }
        return jsonResponse;
    }

    /**
     * 批量新增/更新
     *
     * @param jsonRequest
     * @return
     * @throws IOException
     */
    @Override
    public JsonResponse addGjdqBatch(JsonRequest jsonRequest) throws IOException {
        JsonResponse jsonResponse = new JsonResponse();
        List list = objectMapper.readValue(String.valueOf(jsonRequest.getReqData().get("records")), List.class);
        Iterator it = list.iterator();
        DmGjdq dmGjdq;
        int changeCount;
        List<DmGjdq> successList = new ArrayList<DmGjdq>();
        List<DmGjdq> errorList = new ArrayList<DmGjdq>();
        while (it.hasNext()) {
            dmGjdq = (DmGjdq) it.next();
            if (null == dmGjdq.getUuid()) {
                changeCount = dmGjdqMapper.insertSelective(dmGjdq);
            } else {
                changeCount = dmGjdqMapper.updateByPrimaryKey(dmGjdq);
            }
            if (changeCount > 0) {
                successList.add(dmGjdq);
            } else {
                errorList.add(dmGjdq);
                jsonResponse.setMsg(ConsCommon.WARN_MSG_016);
            }
        }
        return jsonResponse;
    }
}
