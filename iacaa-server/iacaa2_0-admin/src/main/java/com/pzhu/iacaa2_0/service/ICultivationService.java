package com.pzhu.iacaa2_0.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pzhu.iacaa2_0.entity.Cultivation;
import com.pzhu.iacaa2_0.entityVo.CultivationVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-01-15
 */
public interface ICultivationService extends IService<Cultivation> {
    /**
     * 使用实体进行匹配查询
     *
     * @Author ZhaoZezhong
     * @Param: GradRequirement
     * @Return: List<GradRequirement>
     * @Create: 2021/1/22 16:50
     */
    List<Cultivation> voList(CultivationVo vo);

    List<Cultivation> list(CultivationVo vo);


}
