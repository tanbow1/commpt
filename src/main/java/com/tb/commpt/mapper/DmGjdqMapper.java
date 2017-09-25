package com.tb.commpt.mapper;

import com.tb.commpt.model.DmGjdq;
import com.tb.commpt.annotation.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface DmGjdqMapper {
    @Insert({
            "insert into T_DM_GJDQ (GJDQ_MC_Z, GJDQ_MC_E, ",
            "GJDQ_MCDM, GJDQ_DHDM, ",
            "YXBJ, GJDQ_ID,SC)",
            "values (#{gjdqMcZ,jdbcType=VARCHAR}, #{gjdqMcE,jdbcType=VARCHAR}, ",
            "#{gjdqMcdm,jdbcType=VARCHAR}, #{gjdqDhdm,jdbcType=VARCHAR}, ",
            "#{yxbj,jdbcType=CHAR}, #{gjdqId,jdbcType=VARCHAR},#{sc,jdbcType=VARCHAR})"
    })
    int insert(DmGjdq record);

    int insertSelective(DmGjdq record);

    @Delete({
            "delete from T_DM_GJDQ",
            "where UUID = #{uuid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String uuid);

    int updateByPrimaryKey(DmGjdq record);

    List<DmGjdq> selectGjdqList(@Param("pageStart") int pageStart, @Param("pageEnd") int pageEnd);

    @Select({"select count(*) from T_DM_GJDQ "})
    int selectGjdqCount();

    @Select({"select count(GJDQ_ID) from T_DM_GJDQ where GJDQ_ID = #{gjdqId,jdbcType=VARCHAR} "})
    int selectCountByGjdqId(String gjdqId);

    int insertByBatch(List<DmGjdq> gjdqList);

    @Select("SELECT GJDQ_MC_Z, GJDQ_MC_E,GJDQ_MCDM, GJDQ_DHDM,decode(YXBJ,'1','有效','0','无效','--' ) YXBJ, GJDQ_ID,SC FROM T_DM_GJDQ")
    List<Map<String, Object>> selectAllGjdqList();
}