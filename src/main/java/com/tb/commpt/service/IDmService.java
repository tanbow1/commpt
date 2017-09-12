package com.tb.commpt.service;

import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.model.DmAccount;
import com.tb.commpt.model.DmMenu;
import com.tb.commpt.model.JsonRequest;
import com.tb.commpt.model.JsonResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public interface IDmService {

    //dmAccount
    List<DmAccount> selectAllDmAccount();

    DmAccount selectDmAccountByPrimaryKey(String accountType);

    int deleteDmAccountByPrimaryKey(String accountType);

    int saveDmAccount(DmAccount record);

    //dmMenu
    List<DmMenu> selectMenuByPId(String parentId);

    List<ConcurrentHashMap<String, Object>> getMenuTree(String parentId);

    //dmGjdq
    JsonResponse getGjdqListPagination(int pageStart, int pageEnd);

    JsonResponse deleteGjdqBatch(JsonRequest jsonRequest) throws IOException;

    JsonResponse addGjdqBatch(JsonRequest jsonRequest) throws IOException, BizLevelException;

}
