package com.pzhu.iacaa2_0.service;

import com.pzhu.iacaa2_0.entity.StuObjEval;
import com.pzhu.iacaa2_0.entity.Target;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
public interface ITargetService extends IService<Target> {
    //Boolean summaryThisYearTargetsGrade(Integer year);

    List<Target> list(Target target);

    Boolean removeByReqId(Long id);

    Boolean insertBatch(List<Target> targetList);

    List<Target> listAttribute(Target target);

    List<StuObjEval> listEvaluate(StuObjEval stuObjEval);
}
