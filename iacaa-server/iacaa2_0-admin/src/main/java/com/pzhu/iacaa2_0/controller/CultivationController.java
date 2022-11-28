package com.pzhu.iacaa2_0.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gapache.security.annotation.AuthResource;
import com.gapache.security.annotation.NeedAuth;
import com.pzhu.iacaa2_0.common.ActionResult;
import com.pzhu.iacaa2_0.entity.*;
import com.pzhu.iacaa2_0.entityVo.CultivationVo;
import com.pzhu.iacaa2_0.entityVo.GradRequirementVo;
import com.pzhu.iacaa2_0.entityVo.IdsVo;
import com.pzhu.iacaa2_0.entityVo.VersionsVo;
import com.pzhu.iacaa2_0.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
@RestController
@RequestMapping("/cultivation")
@NeedAuth("Cultivation")
public class CultivationController {

    @Autowired
    ICultivationService cultivationService;

    @RequestMapping("/list")
    @AuthResource(scope = "list", name = "培养方案列表")
    public ActionResult list(@RequestBody CultivationVo vo) {
        List<Cultivation> list = cultivationService.list(vo);
        return ActionResult.ofSuccess(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping("/update")
    @AuthResource(scope = "update", name = "更新培养方案列表byVersion")
    public ActionResult update(@RequestBody Cultivation cultivation){
        UpdateWrapper<Cultivation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", cultivation.getId());
        boolean update = cultivationService.update(cultivation,updateWrapper);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200, "更新失败");
    }
    @RequestMapping("/save")
    @AuthResource(scope = "save", name = "保存单个培养方案")
    public ActionResult save(@RequestBody Cultivation cultivation){
        boolean update = cultivationService.save(cultivation);
        return update ? ActionResult.ofSuccess() : ActionResult.ofFail(200, "保存失败");
    }
    //
    @RequestMapping("/del")
    @AuthResource(scope = "del", name = "删除培养方案")
    public ActionResult del(@RequestBody IdsVo ids){
        boolean b = cultivationService.removeByIds(ids.getIds());
        return b ? ActionResult.ofSuccess() : ActionResult.ofFail("删除失败");
    }
}
