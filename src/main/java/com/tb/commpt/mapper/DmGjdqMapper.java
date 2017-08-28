package com.tb.commpt.mapper;

import com.tb.commpt.model.DmGjdq;
import com.tb.commpt.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Insert;

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
}