package com.pzhu.iacaa2_0.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzhu.iacaa2_0.entity.Teacher;
import com.pzhu.iacaa2_0.mapper.TeacherMapper;
import com.pzhu.iacaa2_0.service.ITeacherService;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
    @Override
    public List<Teacher> listInfo(Teacher teacher) {
        return baseMapper.listInfo(teacher);
    }

    @Override
    public Boolean deleteOne(Teacher teacher) {
        return baseMapper.deleteOne(teacher);
    }

    @Override
    public Boolean updateInfo(Teacher teacher) {
        return baseMapper.updateInfo(teacher);
    }

    @Override
    public Boolean insertBatch(List<Teacher> teacherList) {
        return baseMapper.insertBatch(teacherList);
    }

}
