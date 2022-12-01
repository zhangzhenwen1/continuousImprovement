package com.pzhu.iacaa2_0.service.impl;

import com.pzhu.iacaa2_0.entity.Target;
import com.pzhu.iacaa2_0.mapper.TargetMapper;
import com.pzhu.iacaa2_0.service.ICourseObjectiveService;
import com.pzhu.iacaa2_0.service.ICourseTargetService;
import com.pzhu.iacaa2_0.service.ITargetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TargetServiceImpl extends ServiceImpl<TargetMapper, Target> implements ITargetService {
    @Autowired
    ICourseObjectiveService courseTaskService;

    @Autowired
    ICourseTargetService courseTargetService;

    @Override
    public List<Target> list(Target target) {
        return baseMapper.list(target);
    }

    @Transactional
    @Override
    public Boolean removeByReqId(Long id) {
        List<Target> byReqId = baseMapper.getByReqId(id);
        final boolean[] b = {true};
        byReqId.forEach(target -> {
            b[0] = b[0] && courseTargetService.removeByTargetId(target.getId());
        });

        return b[0] && baseMapper.removeByReqId(id) >=0;
    }

    @Override
    @Transactional
    public Boolean insertBatch(List<Target> targetList) {
        return baseMapper.insertBatch(targetList);
    }

}
