package com.pzhu.iacaa2_0.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pzhu.iacaa2_0.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

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
@TableName("t_studentInfo")
public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("studentId")
    private Long studentId;

    @TableField("studentName")
    private String studentName;

    @TableField("grade")
    private String grade;

    @TableField("cultivationId")
    private Integer cultivationId;
}
