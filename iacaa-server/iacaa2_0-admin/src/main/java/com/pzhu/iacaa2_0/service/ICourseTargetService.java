package com.pzhu.iacaa2_0.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzhu.iacaa2_0.entity.CourseTarget;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pzhu.iacaa2_0.entityVo.CourseTargetVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
public interface ICourseTargetService extends IService<CourseTarget> {

    List<CourseTarget> list(CourseTarget courseTarget);

    Boolean removeByTargetId(Long id);

    Boolean remove(CourseTarget courseTarget);

    List<CourseTargetVo> voList(CourseTarget courseTarget);

    boolean insertBatch(List<CourseTarget> courseTargetList);
}
