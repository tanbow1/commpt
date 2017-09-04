package com.tb.commpt.mapper;

import com.tb.commpt.model.XtUserAccount;
import com.tb.commpt.model.XtUserAccountKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface XtUserAccountMapper {
    @Delete({
        "delete from T_XT_USER_ACCOUNT",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ACCOUNT_TYPE = #{accountType,jdbcType=CHAR}",
          "and ACCOUNT = #{account,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(XtUserAccountKey key);

    @Insert({
        "insert into T_XT_USER_ACCOUNT (USER_ID, ACCOUNT_TYPE, ",
        "ACCOUNT, IS_DEFAULT, ",
        "YXBJ, LR_SJ, PASS, ",
        "PASS_ENC)",
        "values (#{userId,jdbcType=VARCHAR}, #{accountType,jdbcType=CHAR}, ",
        "#{account,jdbcType=VARCHAR}, #{isDefault,jdbcType=CHAR}, ",
        "#{yxbj,jdbcType=CHAR}, #{lrSj,jdbcType=TIMESTAMP}, #{pass,jdbcType=VARCHAR}, ",
        "#{passEnc,jdbcType=VARCHAR})"
    })
    int insert(XtUserAccount record);

    int insertSelective(XtUserAccount record);

    @Select({
        "select",
        "USER_ID, ACCOUNT_TYPE, ACCOUNT, IS_DEFAULT, YXBJ, LR_SJ, PASS, PASS_ENC",
        "from T_XT_USER_ACCOUNT",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ACCOUNT_TYPE = #{accountType,jdbcType=CHAR}",
          "and ACCOUNT = #{account,jdbcType=VARCHAR}"
    })
    @ResultMap("com.tb.commpt.mapper.XtUserAccountMapper.BaseResultMap")
    XtUserAccount selectByPrimaryKey(XtUserAccountKey key);

    int updateByPrimaryKeySelective(XtUserAccount record);

    @Update({
        "update T_XT_USER_ACCOUNT",
        "set IS_DEFAULT = #{isDefault,jdbcType=CHAR},",
          "YXBJ = #{yxbj,jdbcType=CHAR},",
          "LR_SJ = #{lrSj,jdbcType=TIMESTAMP},",
          "PASS = #{pass,jdbcType=VARCHAR},",
          "PASS_ENC = #{passEnc,jdbcType=VARCHAR}",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ACCOUNT_TYPE = #{accountType,jdbcType=CHAR}",
          "and ACCOUNT = #{account,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(XtUserAccount record);
}