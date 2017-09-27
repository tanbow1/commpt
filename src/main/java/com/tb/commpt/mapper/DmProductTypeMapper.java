package com.tb.commpt.mapper;

import com.tb.commpt.model.DmProductType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DmProductTypeMapper {
    @Delete({
        "delete from T_DM_PRODUCTTYPE",
        "where TYPE_ID = #{typeId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String typeId);

    @Insert({
        "insert into T_DM_PRODUCTTYPE (TYPE_ID, TYPE_NAME, ",
        "YXBJ, P_ID, TYPE_DESC, ",
        "PX)",
        "values (#{typeId,jdbcType=VARCHAR}, #{typeName,jdbcType=VARCHAR}, ",
        "#{yxbj,jdbcType=CHAR}, #{pId,jdbcType=VARCHAR}, #{typeDesc,jdbcType=VARCHAR}, ",
        "#{px,jdbcType=DECIMAL})"
    })
    int insert(DmProductType record);

    int insertSelective(DmProductType record);

    @Select({
        "select",
        "TYPE_ID, TYPE_NAME, YXBJ, P_ID, TYPE_DESC, PX",
        "from T_DM_PRODUCTTYPE",
        "where TYPE_ID = #{typeId,jdbcType=VARCHAR}"
    })
    @ResultMap("com.tb.commpt.mapper.DmProductTypeMapper.BaseResultMap")
    DmProductType selectByPrimaryKey(String typeId);

    int updateByPrimaryKeySelective(DmProductType record);

    @Update({
        "update T_DM_PRODUCTTYPE",
        "set TYPE_NAME = #{typeName,jdbcType=VARCHAR},",
          "YXBJ = #{yxbj,jdbcType=CHAR},",
          "P_ID = #{pId,jdbcType=VARCHAR},",
          "TYPE_DESC = #{typeDesc,jdbcType=VARCHAR},",
          "PX = #{px,jdbcType=DECIMAL}",
        "where TYPE_ID = #{typeId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DmProductType record);
}