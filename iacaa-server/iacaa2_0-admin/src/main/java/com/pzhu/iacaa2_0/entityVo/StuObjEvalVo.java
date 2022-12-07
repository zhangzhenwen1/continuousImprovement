package com.pzhu.iacaa2_0.entityVo;

import com.pzhu.iacaa2_0.entity.StuObjEval;
import com.pzhu.iacaa2_0.entity.StuScore;
import lombok.Data;

/**
 * @author ZhaoZezhong
 * @version V1.0
 * @Title: StuScoreVo
 * @Description: Company:成都平凡谷科技有限责任公司
 * @date 2021/4/2610:46
 */
@Data
public class StuObjEvalVo extends StuObjEval {
    private Long attributeId;
    private Long subAttributeId;
    private double max;
    private double avg;
    private double min;
}
