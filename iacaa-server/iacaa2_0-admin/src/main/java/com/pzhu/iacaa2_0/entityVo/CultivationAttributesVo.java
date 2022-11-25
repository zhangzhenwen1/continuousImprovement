package com.pzhu.iacaa2_0.entityVo;

import com.pzhu.iacaa2_0.entity.Attributes;
import com.pzhu.iacaa2_0.entity.GradRequirement;
import com.pzhu.iacaa2_0.entity.Cultivation;
import lombok.Data;

import java.util.List;

/**
 * @author MrZhao
 */
@Data
public class CultivationAttributesVo extends Cultivation {
    private Integer cultivationId;
    //private List<Target> targets;
    private List<GradRequirement> gradRequirementList;
}
