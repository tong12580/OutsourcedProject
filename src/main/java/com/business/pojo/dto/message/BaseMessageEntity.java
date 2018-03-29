package com.business.pojo.dto.message;

import lombok.Data;

import java.util.Date;

/**
 * BaseMessage
 * Created by yuTong on 2018/03/29.
 */
@Data
public class BaseMessageEntity {
    // 消息类型
    private String type;

    // 消息内容
    private String content;

    // 发送者
    private String sender;

    // 接受者 类型
    private String toType;

    // 接受者
    private String receiver;

    // 发送时间
    private Date date;
}
