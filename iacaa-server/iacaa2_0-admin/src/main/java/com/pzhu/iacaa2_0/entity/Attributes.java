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
@TableName("t_grad_requirement")
public class Attributes extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 年份
     */
    @TableField("id")
    private Long id;

    /**
     * 毕业要求
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("discrible")
    private String discrible;

}
