package com.tb.commpt.service;

import com.tb.commpt.model.DmAccount;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface IDmService {

    List<DmAccount> selectAllDmAccount();

    DmAccount selectDmAccountByPrimaryKey(String accountType);

    int deleteDmAccountByPrimaryKey(String accountType);

    int saveDmAccount(DmAccount record);


}
