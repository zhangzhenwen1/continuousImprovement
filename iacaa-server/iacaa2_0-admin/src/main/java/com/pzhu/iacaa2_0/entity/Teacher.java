package com.pzhu.iacaa2_0.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pzhu.iacaa2_0.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.text.DateFormat;

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
@TableName("t_teacherInfo")
public class Teacher extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("teacherId")
    private Long teacherId;
    @TableField("teacherName")
    private String teacherName;
    @TableField("teacherGender")
    private String teacherGender;
    @TableField("teacherBirthDate")
    private Date teacherBirthDate;

    @TableField("word")
    private String word;

}
