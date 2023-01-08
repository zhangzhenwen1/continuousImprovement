package com.pzhu.iacaa2_0.controller;


import com.gapache.security.annotation.AuthResource;
import com.gapache.security.annotation.NeedAuth;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.StuObjEval;
import com.pzhu.iacaa2_0.entity.Student;
import com.pzhu.iacaa2_0.service.IStudentService;
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
@RequestMapping("/student")
@NeedAuth("student")
public class StudentController {
    @Autowired
    IStudentService studentService;

    @RequestMapping("listInfo")
    @AuthResource(scope = "listInfo", name = "学生信息列表")
    public ActionResult list(@RequestBody Student student) {
        List<Student> studentList = studentService.listInfo(student);
        return ActionResult.ofSuccess(studentList);
    }

    @RequestMapping("listGrade")
    @AuthResource(scope = "listGrade", name = "年级列表")
    public ActionResult listGrade() {
        List<Student> studentList = studentService.listGrade();
        return ActionResult.ofSuccess(studentList);
    }

    @RequestMapping("deleteOne")
    @AuthResource(scope = "deleteOne", name = "删除学生信息")
    public ActionResult delete(@RequestBody Student student) {
        Boolean b = studentService.deleteOne(student);
        return ActionResult.ofSuccess(b);
    }

    @RequestMapping("updateInfo")
    @AuthResource(scope = "updateInfo", name = "修改学生信息")
    public ActionResult updateInfo(@RequestBody Student student) {
        Boolean b = studentService.updateInfo(student);
        return ActionResult.ofSuccess(b);
    }
    @RequestMapping("insertBatchInfo")
    @AuthResource(scope = "insertBatchInfo", name = "insertBatchInfo")
    public ActionResult insertBatchInfo(@RequestBody List<Student> studentList) {
        Boolean b = studentService.insertBatchInfo(studentList);
        return ActionResult.ofSuccess(b);
    }

    @RequestMapping("listObjectiveEvaluate")
    @AuthResource(scope = "listObjectiveEvaluate", name = "listObjectiveEvaluate")
    public ActionResult listObjectiveEvaluate(@RequestBody StuObjEval stuObjEval) {
        List<StuObjEval> stuObjEvalList = studentService.listObjectiveEvaluate(stuObjEval);
        return ActionResult.ofSuccess(stuObjEvalList);
    }

    @RequestMapping("updateObjectiveEvaluate")
    @AuthResource(scope = "updateObjectiveEvaluate", name = "updateObjectiveEvaluate")
    public ActionResult insertBatch(@RequestBody List<StuObjEval> stuObjEvalList) {
        Boolean b = studentService.insertBatch(stuObjEvalList);
        return ActionResult.ofSuccess(b);
    }

}
