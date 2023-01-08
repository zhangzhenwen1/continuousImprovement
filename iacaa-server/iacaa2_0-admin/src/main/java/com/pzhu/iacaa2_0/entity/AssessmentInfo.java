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
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_assessmentInfo")
public class AssessmentInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 培养方案编制年份
     */
    @TableField("cultivationId")
    private Long cultivationId;

    /**
     * 培养方案名称
     */
    @TableField("assessmentName")
    private String assessmentName;

    /**
     * 培养方案描述
     */
    @TableField("assessmentDescribe")
    private String assessmentDescribe;



}
