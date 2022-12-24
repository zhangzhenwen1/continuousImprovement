package com.pzhu.iacaa2_0.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzhu.iacaa2_0.entity.StuObjEval;
import com.pzhu.iacaa2_0.entity.StuScore;
import com.pzhu.iacaa2_0.entity.Student;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
public interface StudentMapper extends BaseMapper<Student> {

    List<Student> listInfo(Student student);

    Boolean deleteOne(Student student);

    Boolean updateInfo(Student student);
    Boolean insertBatchInfo(List<Student> studentList);

    Boolean insertBatch(List<StuObjEval> stuObjEvalList);

    List<Student> listGrade();

    List<StuObjEval> listObjectiveEvaluate(StuObjEval stuObjEval);
}
