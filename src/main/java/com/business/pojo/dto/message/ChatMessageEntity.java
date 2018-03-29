package com.business.pojo.dto.message;

import lombok.Data;

import java.util.Date;

/**
 * ChatMessageEntity
 * Created by yuTong on 2018/03/29.
 */
@Data
public class ChatMessageEntity {
    private String username;

    private String nickname;

    private String avatar;

    private String content;

    private Date sendTime;
}
