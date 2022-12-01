package com.pzhu.iacaa2_0.entity;

import com.pzhu.iacaa2_0.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_courseObjective")
public class CourseObjective extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 描述
     */
    @TableField("objectiveId")
    private Long objectiveId;

    /**
     * 关联课程
     */
    @TableField("objectiveDescribe")
    private String objectiveDescribe;

    /**
     * 关联指标点
     */
    @TableField("courseId")
    private Long courseId;

    /**
     * 占比
     */
    @TableField("cultivationId")
    private Integer cultivationId;

    /**
     * 年份
     */
    @TableField("attributeId")
    private Long attributeId;

    @TableField("subAttributeId")
    private Long subAttributeId;

    /**
     * 系统录入成绩
     */
    @TableField("objectiveSupportRatio")
    private Double objectiveSupportRatio;

    @TableField("idx_target")
    private Double idx_target;
}
