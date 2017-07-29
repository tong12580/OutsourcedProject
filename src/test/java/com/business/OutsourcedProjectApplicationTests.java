package com.business;

import com.business.dao.users.UserOauthRepository;
import com.business.pojo.dto.user.UserOauthDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OutsourcedProjectApplicationTests {

    @Resource
    private UserOauthRepository userOauthRepository;
	@Test
	public void contextLoads() {
        UserOauthDTO userOauthDTO = userOauthRepository.findOneOauthByUserIdJoinUser(1);
        System.out.print(userOauthDTO);
	}

}
