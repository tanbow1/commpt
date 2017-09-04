package com.tb.commpt.mapper;

import com.tb.commpt.model.XtUserAddress;
import com.tb.commpt.model.XtUserAddressKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface XtUserAddressMapper {
    @Delete({
        "delete from T_XT_USER_ADDRESS",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ADDRESS = #{address,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(XtUserAddressKey key);

    @Insert({
        "insert into T_XT_USER_ADDRESS (USER_ID, ADDRESS, ",
        "IS_DEFAULT, YXBJ, LR_SJ)",
        "values (#{userId,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{isDefault,jdbcType=CHAR}, #{yxbj,jdbcType=CHAR}, #{lrSj,jdbcType=TIMESTAMP})"
    })
    int insert(XtUserAddress record);

    int insertSelective(XtUserAddress record);

    @Select({
        "select",
        "USER_ID, ADDRESS, IS_DEFAULT, YXBJ, LR_SJ",
        "from T_XT_USER_ADDRESS",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ADDRESS = #{address,jdbcType=VARCHAR}"
    })
    @ResultMap("com.tb.commpt.mapper.XtUserAddressMapper.BaseResultMap")
    XtUserAddress selectByPrimaryKey(XtUserAddressKey key);

    int updateByPrimaryKeySelective(XtUserAddress record);

    @Update({
        "update T_XT_USER_ADDRESS",
        "set IS_DEFAULT = #{isDefault,jdbcType=CHAR},",
          "YXBJ = #{yxbj,jdbcType=CHAR},",
          "LR_SJ = #{lrSj,jdbcType=TIMESTAMP}",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ADDRESS = #{address,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(XtUserAddress record);
}