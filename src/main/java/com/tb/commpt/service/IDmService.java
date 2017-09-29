package com.tb.commpt.service;

import com.tb.commpt.exception.BizLevelException;
import com.tb.commpt.model.DmAccount;
import com.tb.commpt.model.DmMenu;
import com.tb.commpt.model.comm.JsonRequest;
import com.tb.commpt.model.comm.JsonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

    JsonResponse importGjdqFromExcel(JsonRequest jsonRequest, MultipartFile[] files);

    JsonResponse exportGjdqToExcel(JsonRequest jsonRequest);

    //dmProductType
    JsonResponse selectProductTypeTreeByParentId(JsonRequest jsonRequest);

    JsonResponse getProductTypeTree(JsonRequest jsonRequest) throws IOException;

    JsonResponse updateSpflBatch(JsonRequest jsonRequest) throws IOException, BizLevelException;

}
