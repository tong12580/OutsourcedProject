package com.business.controller;

import com.business.common.redis.RedisUtil;
import com.business.dao.users.UserInfoRepository;
import com.business.pojo.dto.UserInfo;
import com.business.service.interfaces.users.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yuton on 2016/9/13.
 */
@Slf4j
@RestController
public class TestUsersController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    RedisUtil<String,Object> redisUtil;

    @RequestMapping(value = "/api/getUserInfo")
    public UserInfo getUserInfo() {
//        redisUtil.setString("11", "112");
//        log.info("11:"+redisUtil.getString("11"));
//        redisUtil.set("12", "111", 6L);
//        redisUtil.set("13", userInfoRepository.findFirstByUserName("樱桃"), 5L);
//        log.info("12:"+redisUtil.get("12"));
//        System.out.print("13:"+redisUtil.get("13"));
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserName("萝");
//        redisUtil.set("14", userInfo, 1L);
//        System.out.println("14:"+redisUtil.get("14"));
//        Map<String,Object> map = new HashMap<>();
//        map.put("1",1);
//        map.put("2",2);
//        redisUtil.setMap("map",map);
//        System.out.println("map:"+redisUtil.getMap("map"));
//        System.out.println("mapAllValue:"+redisUtil.getMapAllValue("map"));
//        System.out.println("mapAllKey:"+redisUtil.getMapAllKey("map"));
//        System.out.println("mapValue:"+redisUtil.getMapValue("map","1"));
//        redisUtil.set("mappxx",map);
//        System.out.println("mappxx:"+redisUtil.get("mappxx"));
//        redisUtil.delete("mappxx");
//        redisUtil.delete("map");
//        redisUtil.delete("11");
//        log.info("11:"+redisUtil.getString("11"));
//        System.out.println("mappxx:"+redisUtil.get("mappxx"));
//        System.out.println("map:"+redisUtil.getMap("map"));
//        List<UserInfo> userInfos = userInfoRepository.findAll();
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserName("萝");
//        UserInfo userInfo1 = userInfoRepository.findOne(Example.of(userInfo));
        return userInfoService.fineOne("樱桃");
    }
}
