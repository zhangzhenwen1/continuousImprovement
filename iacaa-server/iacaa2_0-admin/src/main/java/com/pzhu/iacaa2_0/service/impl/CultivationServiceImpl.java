package com.pzhu.iacaa2_0.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzhu.iacaa2_0.entity.AssessmentInfo;
import com.pzhu.iacaa2_0.entity.Cultivation;
import com.pzhu.iacaa2_0.entityVo.CultivationVo;
import com.pzhu.iacaa2_0.mapper.CultivationMapper;
import com.pzhu.iacaa2_0.service.ICultivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@Service
public class CultivationServiceImpl extends ServiceImpl<CultivationMapper, Cultivation> implements ICultivationService {
    @Autowired
    ICultivationService cultivationService;

    @Override
    public List<Cultivation> voList(CultivationVo vo) {
        return baseMapper.voList(vo);
    }

    @Override
    public List<Cultivation> list(CultivationVo vo) {
        return baseMapper.list(vo);
    }

    @Override
    public List<AssessmentInfo> listAssessmentInfo(AssessmentInfo assessmentInfo) {
        return baseMapper.listAssessmentInfo(assessmentInfo);
    }

    @Override
    public boolean delAssessmentInfo(AssessmentInfo assessmentInfo) {
        return baseMapper.delAssessmentInfo(assessmentInfo);
    }

    @Override
    public boolean updateAssessmentInfo(AssessmentInfo assessmentInfo) {
        return baseMapper.updateAssessmentInfo(assessmentInfo);
    }

}
