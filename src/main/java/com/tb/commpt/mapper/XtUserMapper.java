package com.tb.commpt.mapper;

import com.tb.commpt.annotation.mybatis.MyBatisRepository;
import com.tb.commpt.model.XtUser;
import org.apache.ibatis.annotations.*;

@MyBatisRepository
public interface XtUserMapper {
    @Delete({
            "delete from T_XT_USER",
            "where USER_ID = #{userId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String userId);

    @Insert({
            "insert into T_XT_USER (USER_ID, USER_NAME, ",
            "USER_ACCOUNT, REAL_NAME, ",
            "SEX, BIRTHDAY, MOBILE, ",
            "NATIONALITY, CARD_TYPE, ",
            "CARD_NUMBER, ADDR, ",
            "YXBJ, LR_SJ, XG_SJ, ",
            "PASS, PASS_ENC, AVATAR)",
            "values (#{userId,jdbcType=VARCHAR}, #{userName,jdbcType=VARCHAR}, ",
            "#{userAccount,jdbcType=VARCHAR}, #{realName,jdbcType=VARCHAR}, ",
            "#{sex,jdbcType=CHAR}, #{birthday,jdbcType=VARCHAR}, #{mobile,jdbcType=VARCHAR}, ",
            "#{nationality,jdbcType=VARCHAR}, #{cardType,jdbcType=VARCHAR}, ",
            "#{cardNumber,jdbcType=VARCHAR}, #{addr,jdbcType=VARCHAR}, ",
            "#{yxbj,jdbcType=CHAR}, #{lrSj,jdbcType=TIMESTAMP}, #{xgSj,jdbcType=TIMESTAMP}, ",
            "#{pass,jdbcType=VARCHAR}, #{passEnc,jdbcType=VARCHAR}, #{avatar,jdbcType=VARCHAR})"
    })
    int insert(XtUser record);

    int insertSelective(XtUser record);

    @Select({
            "select",
            "USER_ID, USER_NAME, USER_ACCOUNT, REAL_NAME, SEX, BIRTHDAY, MOBILE, NATIONALITY, ",
            "CARD_TYPE, CARD_NUMBER, ADDR, YXBJ, LR_SJ, XG_SJ, PASS, PASS_ENC, AVATAR",
            "from T_XT_USER",
            "where USER_ID = #{userId,jdbcType=VARCHAR}"
    })
    @ResultMap("com.tb.commpt.mapper.XtUserMapper.BaseResultMap")
    XtUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(XtUser record);

    @Update({
            "update T_XT_USER",
            "set USER_NAME = #{userName,jdbcType=VARCHAR},",
            "USER_ACCOUNT = #{userAccount,jdbcType=VARCHAR},",
            "REAL_NAME = #{realName,jdbcType=VARCHAR},",
            "SEX = #{sex,jdbcType=CHAR},",
            "BIRTHDAY = #{birthday,jdbcType=VARCHAR},",
            "MOBILE = #{mobile,jdbcType=VARCHAR},",
            "NATIONALITY = #{nationality,jdbcType=VARCHAR},",
            "CARD_TYPE = #{cardType,jdbcType=VARCHAR},",
            "CARD_NUMBER = #{cardNumber,jdbcType=VARCHAR},",
            "ADDR = #{addr,jdbcType=VARCHAR},",
            "YXBJ = #{yxbj,jdbcType=CHAR},",
            "LR_SJ = #{lrSj,jdbcType=TIMESTAMP},",
            "XG_SJ = #{xgSj,jdbcType=TIMESTAMP},",
            "PASS = #{pass,jdbcType=VARCHAR},",
            "PASS_ENC = #{passEnc,jdbcType=VARCHAR},",
            "AVATAR = #{avatar,jdbcType=VARCHAR}",
            "where USER_ID = #{userId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(XtUser record);

    XtUser selectByUsernameAndPassword(@Param("username") String username,
                                       @Param("password") String password);

    String insert2(XtUser xtUser);
}