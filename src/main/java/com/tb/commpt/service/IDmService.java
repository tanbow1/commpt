package com.tb.commpt.service;

import com.tb.commpt.model.DmAccount;
import com.tb.commpt.model.DmMenu;

import java.util.List;
import java.util.Map;


public interface IDmService {

    //dmAccount
    List<DmAccount> selectAllDmAccount();

    DmAccount selectDmAccountByPrimaryKey(String accountType);

    int deleteDmAccountByPrimaryKey(String accountType);

    int saveDmAccount(DmAccount record);

    //dmMenu
    List<DmMenu> selectMenuByPId(String parentId);

    List<Map<String, Object>> getMenuTree(String parentId);
}
