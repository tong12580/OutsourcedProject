package com.business.dao.users;

import com.business.pojo.dto.user.MessageDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MessageDTORepository
 * Created by yuTong on 2018/03/28.
 */
public interface MessageDTORepository extends JpaRepository<MessageDTO, Long> {
}
