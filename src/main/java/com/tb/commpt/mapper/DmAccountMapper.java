package com.tb.commpt.mapper;

import com.tb.commpt.model.DmAccount;
import com.tb.commpt.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

@MyBatisRepository
public interface DmAccountMapper {
    @Delete({
        "delete from T_DM_ACCOUNT",
        "where ACCOUNT_TYPE = #{accountType,jdbcType=CHAR}"
    })
    int deleteByPrimaryKey(String accountType);

    @Insert({
        "insert into T_DM_ACCOUNT (ACCOUNT_TYPE, ACCOUNT_DES, ",
        "YXBJ)",
        "values (#{accountType,jdbcType=CHAR}, #{accountDes,jdbcType=VARCHAR}, ",
        "#{yxbj,jdbcType=CHAR})"
    })
    int insert(DmAccount record);

    int insertSelective(DmAccount record);

    @Select({
        "select",
        "ACCOUNT_TYPE, ACCOUNT_DES, YXBJ",
        "from T_DM_ACCOUNT",
        "where ACCOUNT_TYPE = #{accountType,jdbcType=CHAR}"
    })
    @ResultMap("com.tb.commpt.mapper.DmAccountMapper.BaseResultMap")
    DmAccount selectByPrimaryKey(String accountType);

    @Select({
            "select",
            "ACCOUNT_TYPE, ACCOUNT_DES, YXBJ",
            "from T_DM_ACCOUNT",
            "where YXBJ = '1'"
    })
    @ResultMap("com.tb.commpt.mapper.DmAccountMapper.BaseResultMap")
    List<DmAccount> selectAll();

    int updateByPrimaryKeySelective(DmAccount record);

    @Update({
        "update T_DM_ACCOUNT",
        "set ACCOUNT_DES = #{accountDes,jdbcType=VARCHAR},",
          "YXBJ = #{yxbj,jdbcType=CHAR}",
        "where ACCOUNT_TYPE = #{accountType,jdbcType=CHAR}"
    })
    int updateByPrimaryKey(DmAccount record);


}