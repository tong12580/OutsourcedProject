package com.business.common.http.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.business.common.date.DateUtil;
import com.business.common.json.JsonUtil;
import com.business.common.uuid.UUIDUtil;
import com.business.pojo.dto.user.RoleDTO;
import com.business.pojo.dto.user.UserDTO;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * JwtToken
 * Created by yuTong on .
 */
public class JwtTokenUtil {

    public static String createToken(UserDTO userDTO, String secret, String issuer) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withClaim("userId", userDTO.getId())
                    .withClaim("role", userDTO.getRoleDTO().getName())
                    .withExpiresAt(DateUtil.addWeeks(new Date(), 1))
                    .withSubject(userDTO.getUsername())
                    .withIssuedAt(new Date())
                    .withJWTId(UUIDUtil.getShortUUid())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException ignore) {
        }
        return null;
    }

    public static boolean validateToken(String token, String secret) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 从token中解析中用户信息
     */
    public static UserDTO getAuthentication(String token) {

        DecodedJWT decodedJWT = JWT.decode(token);
        String authorityString = decodedJWT.getClaim("role").asString();
        UserDTO user = new UserDTO();
        user.setUsername(decodedJWT.getSubject());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(authorityString);
        user.setRoleDTO(roleDTO);
        return user;
    }

    /**
     * 从token中获取用户名
     *
     * @param token String
     */
    public static String getUsername(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }

    /**
     * 刷新token
     *
     * @param oldToken 旧token
     */
    public static String refreshToken(String oldToken, String secret, String issuer) {

        try {
            if (validateToken(oldToken, secret)) {
                DecodedJWT jwt = JWT.decode(oldToken);
                if (StringUtils.isNotBlank(jwt.getSubject())) {
                    return JWT.create()
                            .withIssuer(issuer)
                            .withClaim("userId", jwt.getClaim("userId").asString())
                            .withClaim("role", jwt.getClaim("role").asString())
                            .withExpiresAt(DateUtil.addWeeks(new Date(), 1))
                            .withSubject(jwt.getSubject())
                            .withIssuedAt(new Date())
                            .withJWTId(UUIDUtil.getShortUUid())
                            .sign(Algorithm.HMAC256(secret));
                }
            }
        } catch (UnsupportedEncodingException ignore) {
        }
        return null;
    }

    public static void main(String[] args) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("yt");
        userDTO.setId(1L);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("124");
        userDTO.setRoleDTO(roleDTO);
        String s = JwtTokenUtil.createToken(userDTO, "secret", "joker");
        System.out.println(s);
        DecodedJWT jwt = JWT.decode(s);
        System.out.println(JsonUtil.objectToJson(jwt));
        System.out.println(jwt.getAlgorithm());
        System.out.println(validateToken(s, "secret"));
    }
}