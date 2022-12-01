package com.pzhu.iacaa2_0.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gapache.security.annotation.AuthResource;
import com.gapache.security.annotation.NeedAuth;
import com.google.common.util.concurrent.AtomicDouble;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.CourseObjective;
import com.pzhu.iacaa2_0.entityVo.CourseObjectiveVo;
import com.pzhu.iacaa2_0.service.ICourseObjectiveService;
import com.pzhu.iacaa2_0.service.IStuEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/courseObjective")
@NeedAuth("CourseObjective")
public class CourseObjectiveController {
    @Autowired
    ICourseObjectiveService courseObjectiveService;

    @Autowired
    IStuEvaluationService stuEvaluationService;

    @RequestMapping("/voList")
    @AuthResource(scope = "voList", name = "课程目标Vo列表")
    public ActionResult voList(@RequestBody CourseObjectiveVo vo){
        List<CourseObjectiveVo> courseTaskVos = courseObjectiveService.voList(vo);
        return ActionResult.ofSuccess(courseTaskVos);
    }

    @RequestMapping("/list")
    @AuthResource(scope = "list", name = "课程目标列表")
    public ActionResult list(@RequestBody CourseObjective courseObjective){
        List<CourseObjective> courseObjectives = courseObjectiveService.list(courseObjective);
        return ActionResult.ofSuccess(courseObjectives);
    }

    @RequestMapping("/getOne")
    @AuthResource(scope = "getOne", name = "获取单个课程目标")
    public ActionResult getOne(@RequestBody CourseObjective courseObjective){
        QueryWrapper<CourseObjective> queryWrapper = new QueryWrapper<CourseObjective>();
        if(courseObjective.getId() != null){
            queryWrapper.eq("id", courseObjective.getId());
        }
        if(courseObjective.getCourseId() != null){
            queryWrapper.eq("course_id", courseObjective.getCourseId());
        }

        CourseObjective courseTasks = courseObjectiveService.getOne(queryWrapper);

        return ActionResult.ofSuccess(courseTasks);
    }

    @RequestMapping("/randomlist")
    public ActionResult randomlist(){
        CourseObjective courseObjective = new CourseObjective();
        int randomSize = 4;
        List<CourseObjectiveVo> courseTasks = courseObjectiveService.randomlist(courseObjective,randomSize);
        return ActionResult.ofSuccess(courseTasks);
    }

    @RequestMapping("/delete")
    @AuthResource(scope = "delete", name = "删除单个课程目标")
    public ActionResult delete(@RequestBody CourseObjective courseObjective){
        boolean b = courseObjectiveService.remove(courseObjective);
        return b ? ActionResult.ofSuccess() : ActionResult.ofFail("删除失败");
    }


    @RequestMapping("/saveOrUpdate")
    @AuthResource(scope = "saveOrUpdate", name = "保存或更新课程目标列表")
    public ActionResult saveOrUpdate(@RequestBody List<CourseObjective> courseObjectiveList){
        List<CourseObjective> ArrayList = new ArrayList<>();
        Map<String,Double> checkMap = new HashMap<>();
        AtomicBoolean able = new AtomicBoolean(true);
        courseObjectiveList.forEach(i -> {
            String key = String.format("%S",i.getIdx_target());
            checkMap.merge(key, i.getObjectiveSupportRatio(), Double::sum);
            CourseObjective courseObjective = new CourseObjective();
            courseObjective.setIdx_target(i.getIdx_target());
            courseObjective.setObjectiveSupportRatio(i.getObjectiveSupportRatio());
            ArrayList.add(courseObjective);
        });
        Set<Map.Entry<String, Double>> entries = checkMap.entrySet();
        entries.forEach(entry -> {
            if(entry.getValue() > 1.0000001 || entry.getValue() < 0.9999999d){
                able.set(false);
            }
        });
        if(able.get()){
            return courseObjectiveService.insertBatch(courseObjectiveList)
                    ? ActionResult.ofSuccess()
                    : ActionResult.ofFail(200,"更新失败");
        }else {
            return ActionResult.ofFail(200,"所支撑单个指标点权重总和需等于1");
        }
    }

    @RequestMapping("/summaryCourseTask")
    @AuthResource(scope = "summaryCourseTask", name = "同步课程目标成绩数据")
    public ActionResult summaryCourseTask(@RequestBody CourseObjective courseObjective){
        return ActionResult.ofSuccess(courseObjectiveService.summaryCourseTask(courseObjective.getCultivationId()));
    }

    @RequestMapping("/summaryCourseTaskByCourseId")
    @AuthResource(scope = "summaryCourseTaskByCourseId", name = "同步课程目标成绩数据ById")
    public ActionResult summaryCourseTaskByCourseId(@RequestBody CourseObjective courseObjective){
        return ActionResult.ofSuccess(courseObjectiveService.summaryCourseTaskById(courseObjective));
    }
}
