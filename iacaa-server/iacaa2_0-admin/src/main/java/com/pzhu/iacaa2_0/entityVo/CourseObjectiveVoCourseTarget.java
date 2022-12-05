package com.pzhu.iacaa2_0.entityVo;

import com.pzhu.iacaa2_0.entity.Course;
import com.pzhu.iacaa2_0.entity.CourseObjective;
import com.pzhu.iacaa2_0.entity.CourseTarget;
import com.pzhu.iacaa2_0.entity.Target;
import lombok.Data;

@Data
public class CourseObjectiveVoCourseTarget extends CourseObjective {
    CourseTarget courseTarget;
}
