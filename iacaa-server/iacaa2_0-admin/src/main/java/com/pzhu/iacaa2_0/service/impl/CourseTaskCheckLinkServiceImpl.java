package com.pzhu.iacaa2_0.service.impl;

import com.pzhu.iacaa2_0.entity.CourseTaskCheckLink;
import com.pzhu.iacaa2_0.mapper.CourseTaskCheckLinkMapper;
import com.pzhu.iacaa2_0.service.ICourseTaskCheckLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
@Service
public class CourseTaskCheckLinkServiceImpl extends ServiceImpl<CourseTaskCheckLinkMapper, CourseTaskCheckLink> implements ICourseTaskCheckLinkService {

    @Override
    public List<CourseTaskCheckLink> list(CourseTaskCheckLink courseTaskCheckLink) {
        return baseMapper.list(courseTaskCheckLink);
    }

    @Override
    public Boolean insertBatch(List<CourseTaskCheckLink> courseTaskCheckLinkList) {
        return baseMapper.insertBatch(courseTaskCheckLinkList);
    }

    @Override
    public Boolean remove(CourseTaskCheckLink courseTaskCheckLink) {
        return baseMapper.remove(courseTaskCheckLink);
    }
}
