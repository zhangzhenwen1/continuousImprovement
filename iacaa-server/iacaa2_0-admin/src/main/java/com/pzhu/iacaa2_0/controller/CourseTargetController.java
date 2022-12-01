package com.pzhu.iacaa2_0.controller;


import com.gapache.security.annotation.AuthResource;
import com.gapache.security.annotation.NeedAuth;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.CourseTarget;
import com.pzhu.iacaa2_0.entityVo.CourseTargetVo;
import com.pzhu.iacaa2_0.service.ICourseTargetService;
import com.pzhu.iacaa2_0.service.ICourseObjectiveService;
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
@RequestMapping("/courseTarget")
@NeedAuth("CourseTarget")
public class CourseTargetController {
    @Autowired
    ICourseTargetService courseTargetService;

    @Autowired
    ICourseObjectiveService courseTaskService;

    @RequestMapping("/list")
    @AuthResource(scope = "list", name = "课程-指标点列表")
    public ActionResult list(@RequestBody CourseTarget courseTarget){
        List<CourseTarget> list = courseTargetService.list(courseTarget);
        return ActionResult.ofSuccess(list);
    }

    @RequestMapping("/voList")
    @AuthResource(scope = "voList", name = "课程-指标点Vo列表")
    public ActionResult voList(@RequestBody CourseTarget courseTarget){
        List<CourseTargetVo> voList = courseTargetService.voList(courseTarget);
        return ActionResult.ofSuccess(voList);
    }

    @RequestMapping("/deleteOne")
    @AuthResource(scope = "deleteOne", name = "删除单个课程-指标点")
    public ActionResult deleteOne(@RequestBody CourseTarget courseTarget){
        return courseTargetService.remove(courseTarget)
                ? ActionResult.ofSuccess()
                : ActionResult.ofFail("删除失败");
    }

    //@RequestMapping("/thisYearvoList")
    //@AuthResource(scope = "thisYearvoList", name = "本年度课程-指标点Vo列表")
    //public ActionResult thisYearvoList(@RequestBody CourseTargetVo vo){
    //    List<CourseTargetVo> volist = courseTargetService.volist(vo);
        //volist.removeIf(next -> !next.getTarget().getYear().equals(vo.getYear()));
    //    return ActionResult.ofSuccess(volist);
   //}

    @RequestMapping("/saveOrUpdate")
    @AuthResource(scope = "saveOrUpdate", name = "保存或更新课程-指标点")
    public ActionResult saveOrUpdate(@RequestBody List<CourseTarget> courseTargetList){
        if(courseTargetList.isEmpty()){
            return ActionResult.ofFail("数据不得为空");
        }
        AtomicReference<Float> totalRatio = new AtomicReference<>((float) 0);
        Map<String,String> checkName = new HashMap<>();
        AtomicBoolean nameOk = new AtomicBoolean(true);
        courseTargetList.forEach(i -> {
            if(StringUtils.isEmpty(i.getCourseId()) || checkName.get(i.getCourseId().toString()) != null){
                nameOk.set(false);
            }else {
                checkName.put(i.getCourseId().toString(),"have");
            }
            totalRatio.updateAndGet(v -> (float) (v + i.getSupportRatio()));
        });
        if(!nameOk.get()){
            return ActionResult.ofFail(200,"课程信息不得为空或重复");
        }
        if(totalRatio.get() > 1.000001 || totalRatio.get() < 0.999999999){
            return ActionResult.ofFail(200,"权重总必须为1");
        }
        boolean b = courseTargetService.insertBatch(courseTargetList);
        return b ? ActionResult.ofSuccess() : ActionResult.ofFail(200,"后台异常，更新失败");
    }

}
