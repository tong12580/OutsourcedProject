package com.business.config;

import com.business.dao.auth.RoleDTORepository;
import com.business.pojo.dto.user.RoleDTO;
import com.business.pojo.enums.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 初始化配置
 * InitConfig
 *
 * @author yuTong
 * @version 1.0
 * @since 2019-12-5 15:22
 */
@Slf4j
@Configuration
public class InitConfig {

    @Resource
    private RoleDTORepository roleDTORepository;

    @PostConstruct
    public void postConstruct() {
        initRole();
    }

    private void initRole() {
        long count = roleDTORepository.count();
        if (count > 0) {
            return;
        }
        RoleEnum[] roleEnums = RoleEnum.values();
        List<RoleDTO> roleDTOS = new ArrayList<>(roleEnums.length);
        for (RoleEnum roleEnum : roleEnums) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName(roleEnum.getRoleName());
            roleDTOS.add(roleDTO);
        }
        roleDTORepository.saveAll(roleDTOS);
    }
}
