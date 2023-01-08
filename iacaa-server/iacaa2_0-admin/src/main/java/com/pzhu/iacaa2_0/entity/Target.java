package com.pzhu.iacaa2_0.entity;

import com.pzhu.iacaa2_0.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
@TableName("t_target")
public class Target extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 描述
     */
    @TableField("id")
    private Long id;

    @TableField("idx_target")
    private Long idx_target;
    /**
     * 描述
     */
    @TableField("subAttributeDescribe")
    private String subAttributeDescribe;

    /**
     * 关联毕业要求
     */
    @TableField("attributeId")
    private Long attributeId;

    /**
     * 年份
     */
    @TableField("cultivationId")
    private Integer cultivationId;

}
