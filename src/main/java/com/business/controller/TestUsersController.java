package com.business.controller;

import com.business.common.CommonTools;
import com.business.common.json.JsonUtil;
import com.business.common.message.ResultMessage;
import com.business.common.redis.RedisUtil;
import com.business.common.response.IResult;
import com.business.dao.users.UserInfoRepository;
import com.business.dao.users.UserRepository;
import com.business.pojo.dto.user.UserInfo;
import com.business.pojo.dto.user.UserDTO;
import com.business.service.interfaces.users.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    RedisUtil<String, Object> redisUtil;

    @Resource
    private UserRepository userRepository;

    @RequestMapping(value = "/api/getUser")
    public UserDTO getUser() {
        System.out.print(JsonUtil.objectToJson(userRepository.findOne(1)));
        return userRepository.findOne(1);
    }

    @RequestMapping(value = "/api/insetUser")
    public IResult insetUser(UserDTO user) {
        System.out.print(JsonUtil.objectToJson(user));
        userRepository.save(user);
        return CommonTools.successResult(ResultMessage.STATUS_SUCCESS, userRepository.findOne(1));
    }

    @RequestMapping(value = "/api/getUserInfo")
    public UserInfo getUserInfo() {
        redisUtil.set("11", "112");
//        log.info("11:"+redisUtil.getString("11"));
//        redisUtil.set("12", "111", 6L);
//        redisUtil.set("13", userInfoRepository.findFirstByUserName("樱桃"), 5L);
//        log.info("12:"+redisUtil.get("12"));
//        System.out.print("13:"+redisUtil.get("13"));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("萝");
        redisUtil.set("14", userInfo);
//        System.out.println("14:"+redisUtil.get("14"));
//        Map<String,Object> map = new HashMap<>();
//        map.put("1",1);
//        map.put("2",2);
//        redisUtil.setMap("map",map);
//        System.out.println("map:"+redisUtil.getMap("map"));
//        System.out.println("mapAllValue:"+redisUtil.getMapAllValues("map"));
//        System.out.println("mapAllKey:"+redisUtil.getMapAllKeys("map"));
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
