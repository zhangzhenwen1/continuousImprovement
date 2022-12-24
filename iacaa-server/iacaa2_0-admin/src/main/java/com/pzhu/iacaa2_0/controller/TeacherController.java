package com.pzhu.iacaa2_0.controller;


import com.gapache.security.annotation.AuthResource;
import com.gapache.security.annotation.NeedAuth;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.Teacher;
import com.pzhu.iacaa2_0.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/teacher")
@NeedAuth("teacher")
public class TeacherController {
    @Autowired
    ITeacherService teacherService;
    @RequestMapping("listInfo")
    @AuthResource(scope = "listInfo", name = "listInfo")
    public ActionResult listInfo(@RequestBody Teacher teacher) {
        List<Teacher> teacherList = teacherService.listInfo(teacher);
        return ActionResult.ofSuccess(teacherList);
    }
    @RequestMapping("deleteOne")
    @AuthResource(scope = "deleteOne", name = "deleteOne")
    public ActionResult deleteOne(@RequestBody Teacher teacher) {
        Boolean b =teacherService.deleteOne(teacher);
        return ActionResult.ofSuccess(b);
    }
    @RequestMapping("updateInfo")
    @AuthResource(scope = "updateInfo", name = "updateInfo")
    public ActionResult updateInfo(@RequestBody Teacher teacher) {
        Boolean b =teacherService.updateInfo(teacher);
        return ActionResult.ofSuccess(b);
    }
    @RequestMapping("insertBatch")
    @AuthResource(scope = "insertBatch", name = "insertBatch")
    public ActionResult insertBatch(@RequestBody List<Teacher> teacherList) {
        Boolean b = teacherService.insertBatch(teacherList);
        return ActionResult.ofSuccess(b);
    }
}
