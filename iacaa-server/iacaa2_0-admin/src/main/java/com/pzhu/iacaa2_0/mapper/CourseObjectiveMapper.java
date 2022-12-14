package com.pzhu.iacaa2_0.mapper;

import com.pzhu.iacaa2_0.entity.CourseMeasure;
import com.pzhu.iacaa2_0.entity.CourseObjective;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzhu.iacaa2_0.entityVo.CourseObjectiveVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
public interface CourseObjectiveMapper extends BaseMapper<CourseObjective> {
    List<CourseObjective> getByCourseId(Long id);

    List<CourseObjectiveVo> voList(CourseObjectiveVo vo);

    List<CourseObjective> list(CourseObjective courseObjective);

    Boolean insertBatch(List<CourseObjective> courseObjectiveList);

    Boolean insertMeasure(CourseMeasure courseMeasure);

    CourseMeasure listMeasure(CourseMeasure courseMeasure);

    List<CourseObjective> CourseTargetvoList(CourseObjective courseObjective);

    Boolean delete(CourseObjective courseObjective);
}
