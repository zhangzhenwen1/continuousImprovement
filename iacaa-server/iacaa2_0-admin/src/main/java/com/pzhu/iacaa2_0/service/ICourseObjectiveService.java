package com.pzhu.iacaa2_0.service;

import com.pzhu.iacaa2_0.entity.CourseMeasure;
import com.pzhu.iacaa2_0.entity.CourseObjective;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pzhu.iacaa2_0.entityVo.CourseObjectiveVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
public interface ICourseObjectiveService extends IService<CourseObjective> {

    List<CourseObjectiveVo> voList(CourseObjectiveVo vo);

    List<CourseObjective> list(CourseObjective courseObjective);

    Boolean insertBatch(List<CourseObjective> courseObjectiveList);

    List<CourseObjective> CourseTargetvoList(CourseObjective courseObjective);

    Boolean insertMeasure(CourseMeasure courseMeasure);
    CourseMeasure listMeasure(CourseMeasure courseMeasure);

    Boolean delete(CourseObjective courseObjective);
}
