package com.tb.commpt.mapper;

import com.tb.commpt.model.DmMenu;
import com.tb.commpt.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@MyBatisRepository
public interface DmMenuMapper {
    @Delete({
        "delete from T_DM_MENU",
        "where MENU_ID = #{menuId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String menuId);

    @Insert({
        "insert into T_DM_MENU (MENU_ID, PARENT_ID, ",
        "MENU_NAME, URL, YXBJ, ",
        "OPEN_TYPE)",
        "values (#{menuId,jdbcType=VARCHAR}, #{parentId,jdbcType=VARCHAR}, ",
        "#{menuName,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{yxbj,jdbcType=CHAR}, ",
        "#{openType,jdbcType=CHAR})"
    })
    int insert(DmMenu record);

    int insertSelective(DmMenu record);

    @Select({
        "select",
        "MENU_ID, PARENT_ID, MENU_NAME, URL, YXBJ, OPEN_TYPE",
        "from T_DM_MENU",
        "where MENU_ID = #{menuId,jdbcType=VARCHAR}"
    })
    @ResultMap("com.tb.commpt.mapper.DmMenuMapper.BaseResultMap")
    DmMenu selectByPrimaryKey(String menuId);

    @Select({
            "select",
            "MENU_ID, PARENT_ID, MENU_NAME, URL, YXBJ, OPEN_TYPE",
            "from T_DM_MENU",
            "where YXBJ = '1'"
    })
    @ResultMap("com.tb.commpt.mapper.DmMenuMapper.BaseResultMap")
    List<DmMenu> selectAll();

    int updateByPrimaryKeySelective(DmMenu record);

    @Update({
        "update T_DM_MENU",
        "set PARENT_ID = #{parentId,jdbcType=VARCHAR},",
          "MENU_NAME = #{menuName,jdbcType=VARCHAR},",
          "URL = #{url,jdbcType=VARCHAR},",
          "YXBJ = #{yxbj,jdbcType=CHAR},",
          "OPEN_TYPE = #{openType,jdbcType=CHAR}",
        "where MENU_ID = #{menuId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DmMenu record);
}