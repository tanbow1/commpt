package com.tb.commpt.mapper;

import com.tb.commpt.model.DmZjlx;
import com.tb.commpt.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@MyBatisRepository
public interface DmZjlxMapper {
    @Delete({
        "delete from T_DM_ZJLX",
        "where CARD_TYPE = #{cardType,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String cardType);

    @Insert({
        "insert into T_DM_ZJLX (CARD_TYPE, CARD_NAME, ",
        "YXBJ)",
        "values (#{cardType,jdbcType=VARCHAR}, #{cardName,jdbcType=VARCHAR}, ",
        "#{yxbj,jdbcType=CHAR})"
    })
    int insert(DmZjlx record);

    int insertSelective(DmZjlx record);

    @Select({
        "select",
        "CARD_TYPE, CARD_NAME, YXBJ",
        "from T_DM_ZJLX",
        "where CARD_TYPE = #{cardType,jdbcType=VARCHAR}"
    })
    @ResultMap("com.tb.commpt.mapper.DmZjlxMapper.BaseResultMap")
    DmZjlx selectByPrimaryKey(String cardType);

    @Select({
            "select",
            "CARD_TYPE, CARD_NAME, YXBJ",
            "from T_DM_ZJLX",
            "where YXBJ = '1'"
    })
    @ResultMap("com.tb.commpt.mapper.DmZjlxMapper.BaseResultMap")
    List<DmZjlx> selectAll();

    int updateByPrimaryKeySelective(DmZjlx record);

    @Update({
        "update T_DM_ZJLX",
        "set CARD_NAME = #{cardName,jdbcType=VARCHAR},",
          "YXBJ = #{yxbj,jdbcType=CHAR}",
        "where CARD_TYPE = #{cardType,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(DmZjlx record);
}