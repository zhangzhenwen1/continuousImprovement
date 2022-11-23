package com.pzhu.iacaa2_0.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzhu.iacaa2_0.entity.Course;
import com.pzhu.iacaa2_0.entity.Cultivation;
import com.pzhu.iacaa2_0.entityVo.CourseVo;
import com.pzhu.iacaa2_0.entityVo.CultivationVo;
import com.pzhu.iacaa2_0.mapper.CultivationMapper;
import com.pzhu.iacaa2_0.service.ICourseTargetService;
import com.pzhu.iacaa2_0.service.ICultivationService;
import com.pzhu.iacaa2_0.service.ITargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
