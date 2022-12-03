package com.pzhu.iacaa2_0.entity;

import com.pzhu.iacaa2_0.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course_task_check_link")
public class CourseTaskCheckLink extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 课程目标
     */
    @TableField("objectiveId")
    private Long objectiveId;

    /**
     * 考核环节
     */
    @TableField("courseId")
    private Long courseId;

    @TableField("cultivationId")
    private Integer cultivationId;

    /**
     * 考核环节
     */
    @TableField("assessmentName")
    private String assessmentName;

    /**
     * 支撑权重
     */
    @TableField("ratio")
    private Double ratio;

}
