package com.tb.commpt.mapper;

import com.tb.commpt.model.DmGjdq;
import com.tb.commpt.annotation.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface DmGjdqMapper {
    @Insert({
            "insert into T_DM_GJDQ (GJDQ_MC_Z, GJDQ_MC_E, ",
            "GJDQ_MCDM, GJDQ_DHDM, ",
            "YXBJ, GJDQ_ID)",
            "values (#{gjdqMcZ,jdbcType=VARCHAR}, #{gjdqMcE,jdbcType=VARCHAR}, ",
            "#{gjdqMcdm,jdbcType=VARCHAR}, #{gjdqDhdm,jdbcType=VARCHAR}, ",
            "#{yxbj,jdbcType=CHAR}, #{gjdqId,jdbcType=VARCHAR})"
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
}