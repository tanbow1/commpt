package com.tb.commpt.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.constant.ContentType;
import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.mapper.DmAccountMapper;
import com.tb.commpt.mapper.DmGjdqMapper;
import com.tb.commpt.mapper.DmMenuMapper;
import com.tb.commpt.model.DmAccount;
import com.tb.commpt.model.DmGjdq;
import com.tb.commpt.model.DmMenu;
import com.tb.commpt.model.comm.JsonRequest;
import com.tb.commpt.model.comm.JsonResponse;
import com.tb.commpt.service.IDmService;
import com.tb.commpt.utils.CommonUtil;
import com.tb.commpt.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public JsonResponse getGjdqListPagination(int pageNum, int pageSize) {
        JsonResponse jsonResponse = new JsonResponse();
        Integer[] pageStartAndEnd = CommonUtil.getPageStartAndEnd(pageNum, pageSize);
        int total = dmGjdqMapper.selectGjdqCount();
        if (total > 0) {
            List<DmGjdq> dmGjdqList = dmGjdqMapper.selectGjdqList(pageStartAndEnd[0], pageStartAndEnd[1]);
            if (null != dmGjdqList)
                jsonResponse.getRepData().put("gjdqList", dmGjdqList);
        }
        jsonResponse.getRepData().put("gjdqCount", total);

        return jsonResponse;
    }

    /**
     * 批量删除国籍地区
     *
     * @param jsonRequest
     * @return
     * @throws IOException
     */
    @Transactional(rollbackFor = {Exception.class})
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
            if (null != dmGjdq.getUuid()) {
                deleteCount = dmGjdqMapper.deleteByPrimaryKey(dmGjdq.getUuid());
                if (deleteCount > 0) {
                    successList.add(dmGjdq);
                } else {
                    errorList.add(dmGjdq);
                }
            }
        }
        if (errorList.size() > 0) {
            jsonResponse.setCode(ConsCommon.WARN_CODE_017);
            jsonResponse.setMsg(errorList.size() + "条" + ConsCommon.WARN_MSG_017);
            jsonResponse.getRepData().put("errorList", errorList);
        }
        return jsonResponse;
    }

    /**
     * 批量新增/更新 国籍地区
     *
     * @param jsonRequest
     * @return
     * @throws IOException
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public JsonResponse addGjdqBatch(JsonRequest jsonRequest) throws IOException, BizLevelException {
        JsonResponse jsonResponse = new JsonResponse();
        JavaType javaType = CommonUtil.getCollectionType(ArrayList.class, DmGjdq.class);
        List list = objectMapper.readValue(String.valueOf(jsonRequest.getReqData().get("records")), javaType);
        Iterator it = list.iterator();
        DmGjdq dmGjdq;
        int changeCount;
        List<DmGjdq> successList = new ArrayList<DmGjdq>();
        List<DmGjdq> errorList = new ArrayList<DmGjdq>();
        while (it.hasNext()) {
            dmGjdq = (DmGjdq) it.next();
            if (null == dmGjdq.getUuid()) {
                //add
                changeCount = dmGjdqMapper.insertSelective(dmGjdq);
            } else {
                //update
                changeCount = dmGjdqMapper.updateByPrimaryKey(dmGjdq);
            }
            if (changeCount > 0) {
                int recordCount = dmGjdqMapper.selectCountByGjdqId(dmGjdq.getGjdqId());
                if (recordCount > 1) {
                    throw new BizLevelException(ConsCommon.WARN_MSG_018);
                }
                successList.add(dmGjdq);
            } else {
                errorList.add(dmGjdq);
                jsonResponse.setCode(ConsCommon.WARN_CODE_016);
                jsonResponse.setMsg(ConsCommon.WARN_MSG_016);
            }
        }
        if (errorList.size() > 0) {
            jsonResponse.getRepData().put("errorList", errorList);
        }
        return jsonResponse;
    }

    /**
     * 导入国籍地区
     *
     * @param jsonRequest
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public JsonResponse importGjdqFromExcel(JsonRequest jsonRequest, MultipartFile[] files) {
        JsonResponse jsonResponse = new JsonResponse();
        List<ArrayList<String>> list = new ArrayList<>();
        if (null != files && files.length > 0) {
            for (int i = 0, len = files.length; i < len; i++) {
                String s = files[i].getContentType();
                if (ContentType.getContentType("xlsx").equals(s)) {
                    list = ExcelUtil.readXlsx(files[i], 0, 0);
                } else if (ContentType.getContentType("xls").equals(s)) {
                    list = ExcelUtil.readXls(files[i], 0, 0);
                }
            }

            if (list.size() <= 0) {
                jsonResponse.setCode(ConsCommon.WARN_CODE_020);
                jsonResponse.setMsg(ConsCommon.WARN_MSG_020);
            } else {
                List<DmGjdq> gjdqList = new ArrayList<DmGjdq>();
                DmGjdq gjdq = null;
                ArrayList<String> listItem = null;
                for (int i = 0, len = list.size(); i < len; i++) {
                    gjdq = new DmGjdq();
                    listItem = list.get(i);
                    gjdq.setGjdqMcE(listItem.get(0));
                    gjdq.setGjdqMcZ(listItem.get(1));
                    gjdq.setGjdqMcdm(listItem.get(2));
                    gjdq.setGjdqDhdm(listItem.get(3));
                    gjdq.setSc(listItem.get(4));
                    gjdq.setGjdqId(listItem.get(5));
                    if ("0".equals(listItem.get(6)) || "1".equals(listItem.get(6))) {
                        gjdq.setYxbj(listItem.get(6));
                    }
                    gjdqList.add(gjdq);
                }
                int insertBatchCount = dmGjdqMapper.insertByBatch(gjdqList);
            }
        } else {
            jsonResponse.setCode(ConsCommon.WARN_CODE_019);
            jsonResponse.setMsg(ConsCommon.WARN_MSG_019);
        }

        return jsonResponse;
    }

    @Override
    public JsonResponse exportGjdqToExcel(JsonRequest jsonRequest) {
        return null;
    }
}
