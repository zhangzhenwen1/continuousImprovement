package com.gapache.cloud.auth.server.dao.repository.user;

import com.gapache.cloud.auth.server.dao.entity.UserRoleEntity;
import com.gapache.jpa.BaseJpaRepository;
import com.gapache.user.common.model.vo.UserVO;

import java.util.List;

/**
 * @author HuSen
 * @since 2021/1/26 2:59 下午
 */
public interface UserRoleRepository extends BaseJpaRepository<UserRoleEntity, Long> {

    UserRoleEntity findByUserId(Long userId);
}
