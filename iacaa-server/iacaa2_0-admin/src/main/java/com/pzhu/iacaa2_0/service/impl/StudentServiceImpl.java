package com.pzhu.iacaa2_0.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzhu.iacaa2_0.entity.StuObjEval;
import com.pzhu.iacaa2_0.entity.Student;
import com.pzhu.iacaa2_0.mapper.StudentMapper;
import com.pzhu.iacaa2_0.service.IStudentService;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    @Override
    public List<Student> listGrade() {
        return baseMapper.listGrade();
    }

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
    public Boolean insertBatchInfo(List<Student> studentList) {return baseMapper.insertBatchInfo(studentList);}

    @Override
    public Boolean insertBatch(List<StuObjEval> stuObjEvalList) {
        return baseMapper.insertBatch(stuObjEvalList);
    }

    @Override
    public List<StuObjEval>  listObjectiveEvaluate(StuObjEval stuObjEval) {
        return baseMapper.listObjectiveEvaluate(stuObjEval);
    }

}
