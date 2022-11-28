package com.pzhu.iacaa2_0.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzhu.iacaa2_0.entity.CourseTarget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzhu.iacaa2_0.entityVo.CourseTargetVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
public interface CourseTargetMapper extends BaseMapper<CourseTarget> {
    List<CourseTarget> list(CourseTarget courseTarget);
    Integer removeByTargetId(Long id);
    List<CourseTargetVo> voList(CourseTarget courseTarget);

    Boolean remove(CourseTarget courseTarget);

    boolean insertBatch(List<CourseTarget> courseTargetList);
}
