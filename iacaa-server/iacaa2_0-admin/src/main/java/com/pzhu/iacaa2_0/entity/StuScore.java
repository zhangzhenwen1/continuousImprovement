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
@TableName("t_stuScore")
public class StuScore extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("studentId")
    private Long studentId;

    @TableField("courseId")
    private Long courseId;

    @TableField("semesterId")
    private Long semesterId;

    @TableField("assessmentName")
    private String assessmentName;

    @TableField("score")
    private Double score;
}
