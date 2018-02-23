package com.business.service;

import com.business.common.message.ResultMessage;
import com.business.common.response.IResult;
import com.business.common.response.IResultUtil;
import com.business.dao.users.UserDTORepository;
import com.business.pojo.dto.user.Role;
import com.business.pojo.dto.user.User;
import com.business.pojo.enums.RoleEnum;
import com.business.service.interfaces.auth.AccessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * AccessServiceImpl
 * Created by yuTong on 20180223.
 */
@Service
public class AccessServiceImpl implements AccessService {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDTORepository userDTORepository;


    @Override
    public IResult<String> registered(String username, String password, String role) {
        User user = new User();
        if (StringUtils.isBlank(role)) {
            role = RoleEnum.ROLE_USER.name();
        }
        Role r = new Role();
        r.setName(role);
        user.setRoles(Stream.of(r).collect(toList()));
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        if (null == userDTORepository.save(user)) {
            return IResultUtil.errorResult(ResultMessage.DATABASE_ABNORMAL, "user");
        }
        return IResultUtil.successResult();
    }
}
