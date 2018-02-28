package com.business.common.http.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.business.common.date.DateUtil;
import com.business.common.json.JsonUtil;
import com.business.common.uuid.UUIDUtil;
import com.business.pojo.dto.user.UserDTO;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * JwtToken
 * Created by yuTong on .
 */
public class JwtTokenUtil {
    static String url = "http://www.leftso.com/blog/221.html";

    public static String createToken(UserDTO userDTO, String secret, String issuer) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withClaim("username", userDTO.getUsername())
                    .withClaim("userId", userDTO.getId())
                    .withExpiresAt(DateUtil.addWeeks(new Date(), 1))
                    .withSubject("OutsourcedProject")
                    .withAudience(userDTO.getUsername())
                    .withIssuedAt(new Date())
                    .withJWTId(UUIDUtil.getShortUUid())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("yt");
        userDTO.setId(1L);
        String s = JwtTokenUtil.createToken(userDTO, "secret", "joker");
        System.out.println(s);
        DecodedJWT jwt = JWT.decode(s);
        System.out.println(JsonUtil.objectToJson(jwt));
        System.out.println(jwt.getAlgorithm());
    }
}
