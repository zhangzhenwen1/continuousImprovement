package com.pzhu.iacaa2_0.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzhu.iacaa2_0.entity.Teacher;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
public interface TeacherMapper extends BaseMapper<Teacher> {
    List<Teacher> listInfo(Teacher teacher);

    Boolean deleteOne(Teacher teacher);

    Boolean updateInfo(Teacher teacher);

    Boolean insertBatch(List<Teacher> teacherList);
}
