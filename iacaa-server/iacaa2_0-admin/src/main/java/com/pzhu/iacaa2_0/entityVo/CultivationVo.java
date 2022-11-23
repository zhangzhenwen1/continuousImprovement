package com.pzhu.iacaa2_0.entityVo;

import com.pzhu.iacaa2_0.entity.Cultivation;
import com.pzhu.iacaa2_0.entity.GradRequirement;
import lombok.Data;
import java.util.List;

/**
 * @author MrZhao
 */
@Data
public class CultivationVo extends Cultivation {
    private String word;
    private Cultivation cultivation;
    private List<Cultivation> cultivations;
    private List<GradRequirement> gradRequirements;
}
