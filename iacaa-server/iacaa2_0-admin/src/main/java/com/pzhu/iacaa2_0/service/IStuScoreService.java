package com.pzhu.iacaa2_0.service;

import com.pzhu.iacaa2_0.entity.StuScore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pzhu.iacaa2_0.entityVo.StuScoreVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhaoZezhong
 * @since 2021-04-21
 */
public interface IStuScoreService extends IService<StuScore> {

    List<StuScore> list(StuScore stuScore);

    Boolean summaryAllCheckLinksScore(Integer year);

    Boolean summaryCheckLinkScoreById(Long id);

    List<StuScoreVo> listVo(StuScore stuScore);
}
