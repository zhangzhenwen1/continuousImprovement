package com.pzhu.iacaa2_0.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pzhu.iacaa2_0.entity.StuScore;
import com.pzhu.iacaa2_0.entity.Student;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
public interface IStudentService extends IService<Student> {

    List<Student> listInfo(Student student);

    Boolean deleteOne(Student student);

    Boolean updateInfo(Student student);
}
