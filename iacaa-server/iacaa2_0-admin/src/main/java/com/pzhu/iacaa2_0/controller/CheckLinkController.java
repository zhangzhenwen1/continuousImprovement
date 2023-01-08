package com.pzhu.iacaa2_0.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gapache.security.annotation.AuthResource;
import com.gapache.security.annotation.NeedAuth;
import com.github.pagehelper.PageInfo;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.CheckLink;
import com.pzhu.iacaa2_0.entity.CourseObjective;
import com.pzhu.iacaa2_0.entityVo.CheckLinkVo;
import com.pzhu.iacaa2_0.service.ICheckLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
@RequestMapping("/checkLink")
@NeedAuth("CheckLink")
public class CheckLinkController {
    @Autowired
    ICheckLinkService checkLinkService;

    @RequestMapping("/list")
    @AuthResource(scope = "list", name = "考核环节列表")
    public ActionResult list(@RequestBody CheckLink checkLink) throws Exception{
        List<CheckLink> list = checkLinkService.list(checkLink);
        return ActionResult.ofSuccess(list);
    }

    @RequestMapping("/delete")
    @AuthResource(scope = "delete", name = "删除单个考核环节")
    public ActionResult delete(@RequestBody CheckLink checkLink){
        QueryWrapper<CheckLink> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("assessmentName", checkLink.getAssessmentName());
        queryWrapper.eq("courseId", checkLink.getCourseId());
        queryWrapper.eq("cultivationId", checkLink.getCultivationId());
        return checkLinkService.remove(queryWrapper)
                ? ActionResult.ofSuccess()
                : ActionResult.ofFail(200,"删除失败");
    }

    @RequestMapping("/pageList")
    @AuthResource(scope = "pageList", name = "考核环节分页列表")
    public ActionResult pageList(@RequestBody CheckLinkVo vo) {
//        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<CheckLink> list = checkLinkService.list(vo);
        PageInfo page = new PageInfo(list);
        return ActionResult.ofSuccess(page);
    }

    @RequestMapping("/saveOrUpdate")
    @AuthResource(scope = "saveOrUpdate", name = "更新或保存考核环节列表")
    public ActionResult saveOrUpdate(@RequestBody List<CheckLink> checkLinks){
        AtomicReference<Boolean> check = new AtomicReference<>(true);
        checkLinks.forEach(i -> {
            if(i.getTotalScore() <= 0){
                check.set(false);
            }
        });
        if(check.get()){
            checkLinkService.insertBatch(checkLinks);
            return ActionResult.ofSuccess();
        }else {
            return ActionResult.ofFail("成绩不能小于或等于0");
        }
    }
}
