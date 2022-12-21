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
@TableName("t_courseMeasure")
public class CourseMeasure extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("courseId")
    private Long courseId;

    @TableField("semesterId")
    private Long semesterId;

    @TableField("measure")
    private String measure;

}
