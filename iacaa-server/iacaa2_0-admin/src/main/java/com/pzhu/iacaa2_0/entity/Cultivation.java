package com.pzhu.iacaa2_0.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pzhu.iacaa2_0.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
@TableName("t_cultivation")
public class Cultivation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 培养方案编制年份
     */
    @TableField("id")
    private Long id;

    /**
     * 培养方案名称
     */
    @TableField("cultivationName")
    private String cultivationName;

    /**
     * 培养方案描述
     */
    @TableField("cultivationDescribe")
    private String cultivationDescribe;



}
