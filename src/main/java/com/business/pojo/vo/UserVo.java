package com.business.pojo.vo;

import com.business.pojo.dto.user.UserDTO;
import lombok.Getter;

import java.sql.Timestamp;


/**
 * @author yuton
 * @version 1.0
 * @description 用户信息View
 * @since 2017/2/8 10:31
 */
@Getter
public class UserVo {

    private Integer id;
    private String userName;
    private String nickName;
    private String phone;
    private String email;
    private String ip;
    private String sex;
    private String idNumber;
    private String address;
    private String inviteCode;
    private String openid;
    private String image;
    private Timestamp createTime;
    private Timestamp updateTime;

    public static class Builder {
        private UserVo vo = new UserVo();

        public Builder builder(UserDTO userDTO) {
            if (null == userDTO) {
                return this;
            }
            vo.id = userDTO.getId();
            vo.userName = userDTO.getUserName();
            vo.nickName = userDTO.getNickName();
            vo.phone = userDTO.getPhone();
            vo.email = userDTO.getEmail();
            vo.ip = userDTO.getIp();
            vo.sex = userDTO.getSex();
            vo.idNumber = userDTO.getIdNumber();
            vo.address = userDTO.getAddress();
            vo.inviteCode = userDTO.getInviteCode();
            vo.openid = userDTO.getOpenid();
            vo.image = userDTO.getImage();
            vo.createTime = userDTO.getCreateTime();
            vo.updateTime = userDTO.getUpdateTime();
            return this;
        }

        public UserVo build() {
            return vo;
        }
    }
}
