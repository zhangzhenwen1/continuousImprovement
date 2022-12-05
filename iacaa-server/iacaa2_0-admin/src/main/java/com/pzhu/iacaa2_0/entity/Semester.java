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
@TableName("t_semesterInfo")
public class Semester extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("semesterId")
    private Long semesterId;

    @TableField("semesterDescribe")
    private String semesterDescribe;

    @TableField("cultivationId")
    private Integer cultivationId;
}
