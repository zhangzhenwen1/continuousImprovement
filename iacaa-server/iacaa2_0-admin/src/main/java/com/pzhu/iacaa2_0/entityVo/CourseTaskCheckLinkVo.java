package com.pzhu.iacaa2_0.entityVo;

import com.pzhu.iacaa2_0.entity.CheckLink;
import com.pzhu.iacaa2_0.entity.CourseObjective;
import com.pzhu.iacaa2_0.entity.CourseTaskCheckLink;
import lombok.Data;

/**
 * @author ZhaoZezhong
 * @version V1.0
 * @Title: CourseTaskCheckLinkVo
 * @Description: Company:成都平凡谷科技有限责任公司
 * @date 2021/4/2210:40
 */
@Data
public class CourseTaskCheckLinkVo extends CourseTaskCheckLink {
    CourseObjective courseObjective;
    CheckLink checkLink;
}
