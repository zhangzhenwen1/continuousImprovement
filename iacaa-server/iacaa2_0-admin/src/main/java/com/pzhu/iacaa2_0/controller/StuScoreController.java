package com.pzhu.iacaa2_0.controller;


import com.gapache.security.annotation.AuthResource;
import com.gapache.security.annotation.NeedAuth;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.*;
import com.pzhu.iacaa2_0.entityVo.StuScoreVo;
import com.pzhu.iacaa2_0.service.ICheckLinkService;
import com.pzhu.iacaa2_0.service.IStuScoreService;
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
@RequestMapping("/stuScore")
@NeedAuth("StuScore")
public class StuScoreController {
    @Autowired
    IStuScoreService stuScoreService;

    @Autowired
    ICheckLinkService checkLinkService;

    @RequestMapping("list")
    @AuthResource(scope = "list", name = "学生成绩列表")
    public ActionResult list(@RequestBody StuScore stuScore){
        List<StuScore> stuScoreList = stuScoreService.list(stuScore);
        return ActionResult.ofSuccess(stuScoreList);
    }

    @RequestMapping("listVo")
    @AuthResource(scope = "listVo", name = "学生成绩列表包含姓名")
    public ActionResult listVo(@RequestBody StuScore stuScore){
        List<StuScoreVo> stuScoreList = stuScoreService.listVo(stuScore);
        return ActionResult.ofSuccess(stuScoreList);
    }
    @RequestMapping("insertBatchScore")
    @AuthResource(scope = "insertBatchScore", name = "insertBatchScore")
    public ActionResult insertBatchScore(@RequestBody List<StuScore> stuScoreList) {
        Boolean b = stuScoreService.insertBatchScore(stuScoreList);
        return ActionResult.ofSuccess(b);
    }
}
