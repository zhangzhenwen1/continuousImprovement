package com.pzhu.iacaa2_0.entityVo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.pzhu.iacaa2_0.entity.StuScore;
import com.pzhu.iacaa2_0.entity.Student;
import lombok.Data;

/**
 * @author ZhaoZezhong
 * @version V1.0
 * @Title: StuScoreVo
 * @Description: Company:成都平凡谷科技有限责任公司
 * @date 2021/4/2610:46
 */
@Data
public class StuScoreVo extends StuScore {
    private Integer cultivationId;
    private Long courseId;
    private String studentName;
    private String grade;
}
