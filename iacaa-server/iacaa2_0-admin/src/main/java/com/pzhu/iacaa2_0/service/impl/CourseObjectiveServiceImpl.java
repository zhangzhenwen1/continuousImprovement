package com.pzhu.iacaa2_0.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzhu.iacaa2_0.entity.CourseObjective;
import com.pzhu.iacaa2_0.entityVo.CourseObjectiveVo;
import com.pzhu.iacaa2_0.mapper.CourseObjectiveMapper;
import com.pzhu.iacaa2_0.service.ICheckLinkService;
import com.pzhu.iacaa2_0.service.ICourseObjectiveService;
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
public class CourseObjectiveServiceImpl extends ServiceImpl<CourseObjectiveMapper, CourseObjective> implements ICourseObjectiveService {
    @Autowired
    ICheckLinkService checkLinkService;

    @Override
    public List<CourseObjectiveVo> voList(CourseObjectiveVo vo) {
        return baseMapper.voList(vo);
    }

    @Override
    public List<CourseObjective> list(CourseObjective courseObjective) {
        return baseMapper.list(courseObjective);
    }

    @Override
    public Boolean insertBatch(List<CourseObjective> courseObjectiveList) {
        return baseMapper.insertBatch(courseObjectiveList);
    }

    @Override
    public Boolean remove(CourseObjective courseObjective) {
        return baseMapper.remove(courseObjective);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean summaryCourseTask(Integer year){
        QueryWrapper<CourseObjective> courseTaskQueryWrapper = new QueryWrapper<>();
        courseTaskQueryWrapper.eq("year",year);
        List<CourseObjective> courseObjectives = baseMapper.selectList(courseTaskQueryWrapper);
        courseObjectives.forEach(i -> {
            checkLinkService.summaryByCourseTaskID(i.getId(),year);
            baseMapper.summaryStuScore(i.getId());
        });
        baseMapper.coverNullToZero();
        return true;
    }

    @Override
    public List<CourseObjectiveVo> randomlist(CourseObjective courseObjective, int randomSize) {
        return baseMapper.randomlist(courseObjective,randomSize);
    }

    @Override
    public Boolean summaryCourseTaskById(CourseObjective courseObjective) {
        QueryWrapper<CourseObjective> courseTaskQueryWrapper = new QueryWrapper<>();
        courseTaskQueryWrapper.eq("course_id", courseObjective.getCourseId());
        List<CourseObjective> courseObjectives = baseMapper.selectList(courseTaskQueryWrapper);
        courseObjectives.forEach(i -> {
            baseMapper.summaryStuScore(i.getId());
        });
        baseMapper.coverNullToZero();
        return true;
    }

}
