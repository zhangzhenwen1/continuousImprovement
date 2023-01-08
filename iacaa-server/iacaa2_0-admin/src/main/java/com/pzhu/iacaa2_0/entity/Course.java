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
 * @since 2021-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_course")
public class Course extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("id")
    private String id;

    /**
     * 内容
     */
    @TableField("name")
    private String name;
    /**
     * 图片地址
     */
    @TableField("courseType")
    private String courseType;
    @TableField("credits")
    private Integer credits;
    @TableField("creditHours")
    private Integer creditHours;
    /**
     * 管理账户
     */
    @TableField("edit_user_id")
    private Integer editUserId;
    @TableField("edit_status")
    private Integer editStatus;

}
