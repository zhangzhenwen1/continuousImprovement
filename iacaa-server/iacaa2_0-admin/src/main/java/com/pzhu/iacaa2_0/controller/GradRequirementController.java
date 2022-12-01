package com.pzhu.iacaa2_0.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gapache.security.annotation.AuthResource;
import com.gapache.security.annotation.NeedAuth;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.*;
import com.pzhu.iacaa2_0.entityVo.GradRequirementVo;
import com.pzhu.iacaa2_0.entityVo.IdsVo;
import com.pzhu.iacaa2_0.service.*;
import com.pzhu.iacaa2_0.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/gradRequirement")
@NeedAuth("GradRequirement")
public class GradRequirementController{

    @Autowired
    IGradRequirementService gradRequirementService;
    @Autowired
    ITargetService targetService;

    @Autowired
    ICourseService courseService;

    @Autowired
    ICourseTargetService courseTargetService;

    @Autowired
    ICourseObjectiveService courseTaskService;

    @Autowired
    ICheckLinkService checkLinkService;

    @Autowired
    ICourseTaskCheckLinkService courseTaskCheckLinkService;

    @Autowired
    IStuScoreService stuScoreService;

    @Autowired
    IStuEvaluationService stuEvaluationService;

    @RequestMapping("/list")
    @AuthResource(scope = "list", name = "毕业要求列表")
    public ActionResult list(@RequestBody GradRequirementVo gradRequirement) {
        //QueryWrapper<GradRequirement> wrapper = new QueryWrapper<>();
        //wrapper.eq("cultivationId", gradRequirement.getCultivationId());
        List<GradRequirement> list = gradRequirementService.list(gradRequirement);

        return ActionResult.ofSuccess(list);
    }

//    @RequestMapping("/pageList")
//    @AuthResource(scope = "pageList", name = "毕业要求列表分页")
//    public ActionResult pageList(@RequestBody GradRequirementVo vo) {
//        QueryWrapper<GradRequirement> wrapper = new QueryWrapper<>();
//        if (!StringUtils.isEmpty(vo.getWord())) {
//            wrapper.like("name", vo.getWord()).or()
//                    .like("discrible", vo.getWord());
//        }
//        if (vo.getYear() != null) {
//            wrapper.eq("year", vo.getYear());
//        }
////        wrapper.orderByDesc("i");
////        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
//        List<GradRequirement> list = gradRequirementService.list(wrapper);
//        return ActionResult.ofSuccess(list);
//    }


    @RequestMapping("/voList")
    @AuthResource(scope = "voList", name = "毕业要求Vo列表")
    public ActionResult voList(@RequestBody GradRequirementVo vo) {
        List<GradRequirementVo> list = gradRequirementService.voList(vo);
        return ActionResult.ofSuccess(list);
    }

    @RequestMapping("/getOne")
    @AuthResource(scope = "getOne", name = "获取单个毕业要求")
    public ActionResult getOne(@RequestBody GradRequirementVo vo) {
        List<GradRequirementVo> list = gradRequirementService.voList(vo);
        return list == null ? ActionResult.ofFail(500,"没有该数据") : ActionResult.ofSuccess(list.get(0));
    }

    @RequestMapping("/voListAll")
    @AuthResource(scope = "voListAll", name = "毕业要求Vo全部列表")
    public ActionResult voListAll(@RequestBody GradRequirementVo vo) {
        List<GradRequirementVo> list = gradRequirementService.voList(vo);
        return ActionResult.ofSuccess(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/update")
    @AuthResource(scope = "update", name = "更新毕业要求")
    public ActionResult update(@RequestBody GradRequirementVo vo) {
        List<Target> targets = vo.getTargets();
        targetService.saveOrUpdateBatch(targets);
        UpdateWrapper<GradRequirement> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", vo.getId());
        boolean update = gradRequirementService.update(vo, updateWrapper);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200, "更新失败");
    }

    @RequestMapping("/update2")
    @AuthResource(scope = "update2", name = "更新毕业要求")
    public ActionResult insertBatch(@RequestBody List<GradRequirement> gradRequirementList) {
        //List<GradRequirement> gradRequirementList = vo.getGradRequirementList();
        //gradRequirementList.forEach(i->{
        //    gradRequirementService.saveOrUpdate(i);
        //        }
        //);
        boolean update = gradRequirementService.insertBatch(gradRequirementList);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200, "更新失败");
    }

    @RequestMapping("/save")
    @AuthResource(scope = "save", name = "保存毕业要求")
    public ActionResult save(@RequestBody GradRequirement gradRequirement) {
        boolean update = gradRequirementService.save(gradRequirement);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200, "添加失败");
    }

    @RequestMapping("/deleteList")
    @AuthResource(scope = "deleteList", name = "删除毕业要求列表")
    public ActionResult deleteList(@RequestBody IdsVo ids) {
        boolean b = gradRequirementService.removeList(ids.getIds());
        return b ? ActionResult.ofSuccess() : ActionResult.ofFail("删除失败");
    }

    @RequestMapping("/deleteOne")
    @AuthResource(scope = "deleteOne", name = "删除单个毕业要求")
    public ActionResult deleteOne(@RequestBody GradRequirement gradRequirement) {
        boolean b = gradRequirementService.removeById(gradRequirement.getId());
        return b ? ActionResult.ofSuccess() : ActionResult.ofFail("删除失败");
    }

    @RequestMapping("/delete")
    @AuthResource(scope = "delete", name = "删除单个毕业要求")
    public ActionResult delete(@RequestBody GradRequirement gradRequirement) {
        QueryWrapper<GradRequirement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", gradRequirement.getId());
        queryWrapper.eq("cultivationId", gradRequirement.getCultivationId());
        boolean b = gradRequirementService.remove(queryWrapper);
        return b ? ActionResult.ofSuccess() : ActionResult.ofFail("删除失败");
    }

    @RequestMapping("/exportTemplate")
    @AuthResource(scope = "exportTemplate", name = "导出模板")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        FileUtils.download("D:/doc/","import.xlsx",response);
    }
/*
    @RequestMapping("/summaryAll")
    @AuthResource(scope = "summaryAll", name = "同步本年度成绩数据")
    public ActionResult summaryAll(@RequestBody GradRequirement gradRequirement) {
        Boolean aBoolean = gradRequirementService.summaryThisYearReqGrade(gradRequirement.getYear());
        return aBoolean ? ActionResult.ofSuccess() : ActionResult.ofFail("统计失败");
    }
 */
}
