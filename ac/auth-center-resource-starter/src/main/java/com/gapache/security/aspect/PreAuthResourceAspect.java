package com.gapache.security.aspect;

import com.gapache.commons.model.AuthConstants;
import com.gapache.security.annotation.AuthResource;
import com.gapache.security.cache.AuthResourceCache;
import com.gapache.commons.model.SecurityException;
import com.gapache.security.holder.AccessCardHolder;
import com.gapache.security.model.AccessCard;
import com.gapache.security.model.SecurityError;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author HuSen
 * @since 2020/8/10 9:08 上午
 */
@Slf4j
@Aspect
@Component
public class PreAuthResourceAspect {

    @Before("@annotation(authResource)")
    public void before(AuthResource authResource) {
        log.warn("标记标记标记 authResource check start");
        AccessCard accessCard = AccessCardHolder.getContext();
        if (accessCard == null) {
            log.warn("accessCard is null");
            throw new SecurityException(SecurityError.FORBIDDEN);
        }
        // 0表示来自任意内部调用
        if (accessCard.getUserId() != null && accessCard.getUserId() == 0) {
            return;
        }
        log.warn("标记标记标记 校验权限 start");
        // 校验权限
        String fullScope = AuthResourceCache.checkFullScope(authResource);
        if (!accessCard.getAuthorities().contains(fullScope)) {
            throw new SecurityException(SecurityError.FORBIDDEN);
        }
        log.warn("标记标记标记 校验用户是否已被禁用 start");
        // 校验用户是否已被禁用
        if (accessCard.getCustomerInfo()==null) {
            throw new SecurityException(SecurityError.CustomerInfo_NOT_FOUND);
        } else {
            Object isEnabled = accessCard.getCustomerInfo().get(AuthConstants.IS_ENABLED);
            if (authResource.checkEnabled() && isEnabled != null && !(Boolean) isEnabled) {
                throw new SecurityException(SecurityError.USER_DISABLED);
            }
            log.warn("标记标记标记 authResource check END");
        }
    }
}
