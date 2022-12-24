package com.pzhu.iacaa2_0.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pzhu.iacaa2_0.entity.Teacher;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
public interface ITeacherService extends IService<Teacher> {

    List<Teacher> listInfo(Teacher teacher);

    Boolean deleteOne(Teacher teacher);

    Boolean updateInfo(Teacher teacher);

    Boolean insertBatch(List<Teacher> teacherList);
}
