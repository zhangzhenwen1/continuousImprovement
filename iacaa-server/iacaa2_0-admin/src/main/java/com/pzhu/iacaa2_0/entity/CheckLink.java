package com.pzhu.iacaa2_0.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_check_link")
public class CheckLink extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 对应课程
     */
    @TableField("courseId")
    private Long courseId;

    /**
     * 标题
     */
    @TableField("assessmentName")
    private String assessmentName;

    /**
     * 标题
     */
    @TableField("cultivationId")
    private Integer cultivationId;

    /**
     * 目标成绩
     */
    @TableField("totalScore")
    private Double totalScore;

    /**
     * 平均成绩
     */
    @TableField("scoreRatio")
    private Double scoreRatio;

}
