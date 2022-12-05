package com.pzhu.iacaa2_0.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzhu.iacaa2_0.entity.CheckLink;
import com.pzhu.iacaa2_0.entity.StuScore;
import com.pzhu.iacaa2_0.entity.Student;
import com.pzhu.iacaa2_0.entityVo.CheckLinkVo;
import com.pzhu.iacaa2_0.mapper.StuScoreMapper;
import com.pzhu.iacaa2_0.mapper.StudentMapper;
import com.pzhu.iacaa2_0.service.ICheckLinkService;
import com.pzhu.iacaa2_0.service.IStuScoreService;
import com.pzhu.iacaa2_0.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Override
    public List<Student> listInfo(Student student) {
        return baseMapper.listInfo(student);
    }

    @Override
    public Boolean deleteOne(Student student) {
        return baseMapper.deleteOne(student);
    }

    @Override
    public Boolean updateInfo(Student student) {
        return baseMapper.updateInfo(student);
    }

}
