package com.pzhu.iacaa2_0.mapper;

import com.pzhu.iacaa2_0.entity.CheckLink;
import com.pzhu.iacaa2_0.entity.CourseTarget;
import com.pzhu.iacaa2_0.entity.CourseTaskCheckLink;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzhu.iacaa2_0.entityVo.CourseTaskCheckLinkVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
public interface CourseTaskCheckLinkMapper extends BaseMapper<CourseTaskCheckLink> {

    List<CourseTaskCheckLink> list(CourseTaskCheckLink courseTaskCheckLink);

    Boolean insertBatch(List<CourseTaskCheckLink> courseTaskCheckLinkList);

    Boolean remove(CourseTaskCheckLink courseTaskCheckLink);
}
