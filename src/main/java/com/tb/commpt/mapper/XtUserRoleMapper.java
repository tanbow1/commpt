package com.tb.commpt.mapper;

import com.tb.commpt.model.XtUserRole;
import com.tb.commpt.model.XtUserRoleKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface XtUserRoleMapper {
    @Delete({
        "delete from T_XT_USERANDROLE",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ROLE_ID = #{roleId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(XtUserRoleKey key);

    @Insert({
        "insert into T_XT_USERANDROLE (USER_ID, ROLE_ID, ",
        "YXBJ)",
        "values (#{userId,jdbcType=VARCHAR}, #{roleId,jdbcType=VARCHAR}, ",
        "#{yxbj,jdbcType=CHAR})"
    })
    int insert(XtUserRole record);

    int insertSelective(XtUserRole record);

    @Select({
        "select",
        "USER_ID, ROLE_ID, YXBJ",
        "from T_XT_USERANDROLE",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ROLE_ID = #{roleId,jdbcType=VARCHAR}"
    })
    @ResultMap("com.tb.commpt.mapper.XtUserRoleMapper.BaseResultMap")
    XtUserRole selectByPrimaryKey(XtUserRoleKey key);

    int updateByPrimaryKeySelective(XtUserRole record);

    @Update({
        "update T_XT_USERANDROLE",
        "set YXBJ = #{yxbj,jdbcType=CHAR}",
        "where USER_ID = #{userId,jdbcType=VARCHAR}",
          "and ROLE_ID = #{roleId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(XtUserRole record);
}