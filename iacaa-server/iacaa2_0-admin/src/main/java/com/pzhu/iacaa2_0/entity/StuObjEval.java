package com.pzhu.iacaa2_0.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pzhu.iacaa2_0.base.BaseEntity;
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
@TableName("t_objectiveEvaluate")
public class StuObjEval extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("studentId")
    private Long studentId;

    @TableField("courseId")
    private Long courseId;

    @TableField("objectiveId")
    private Long objectiveId;

    @TableField("attributeId")
    private Long attributeId;

    @TableField("subAttributeId")
    private Long subAttributeId;

    @TableField("semesterId")
    private Long semesterId;

    @TableField("cultivationId")
    private Integer cultivationId;

    @TableField("evaluate")
    private Double eval;

    @TableField("subAttributeEvaluate")
    private Double subAttributeEvaluate;
}
