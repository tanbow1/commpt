package com.tb.commpt.service;

import com.tb.commpt.model.DmAccount;

import java.util.List;


public interface IDmService {

    List<DmAccount> selectAllDmAccount();

    DmAccount selectDmAccountByPrimaryKey(String accountType);

    int deleteDmAccountByPrimaryKey(String accountType);

    int saveDmAccount(DmAccount record);


}
