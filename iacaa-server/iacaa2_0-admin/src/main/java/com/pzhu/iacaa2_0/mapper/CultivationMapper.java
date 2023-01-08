package com.pzhu.iacaa2_0.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzhu.iacaa2_0.entity.AssessmentInfo;
import com.pzhu.iacaa2_0.entity.Cultivation;
import com.pzhu.iacaa2_0.entityVo.CultivationVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
public interface CultivationMapper extends BaseMapper<Cultivation> {

    List<Cultivation> voList(CultivationVo vo);

    List<Cultivation> list(CultivationVo vo);
    List<AssessmentInfo> listAssessmentInfo(AssessmentInfo assessmentInfo);

    boolean delAssessmentInfo(AssessmentInfo assessmentInfo);

    boolean updateAssessmentInfo(AssessmentInfo assessmentInfo);
}
