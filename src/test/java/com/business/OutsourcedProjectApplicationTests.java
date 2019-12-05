package com.business;

import com.business.common.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OutsourcedProjectApplicationTests {

    @Resource
    private RedisUtil<String, String> redisUtil;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedis() {
        System.out.println(redisUtil.ping());
    }
}
