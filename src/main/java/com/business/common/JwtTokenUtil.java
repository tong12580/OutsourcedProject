package com.business.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jokers.common.uuid.UUIDUtil;
import com.jokers.pojo.bo.JwtBo;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * JwtTokenUtil
 * Created by yuTong on 2018/03/08.
 */
public class JwtTokenUtil extends com.jokers.common.http.token.JwtTokenUtil{
    private final static String ROLE = "role";
    private final static String USER_ID = "userId";

    /**
     * token有效性校验
     *
     * @param token String
     * @param secret String
     * @return boolean
     */
    public static boolean validateToken(String token, String secret) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        } catch (TokenExpiredException | UnsupportedEncodingException e) {
            return false;
        }
        return true;
    }

    /**
     * 刷新token
     *
     * @param oldToken 旧token
     * @return String
     */
    public static String refreshToken(String oldToken, JwtBo jwtBo) {
        try {
            if (!validateToken(oldToken, jwtBo.getSecret())) {
                DecodedJWT jwt = JWT.decode(oldToken);
                if (StringUtils.isNotBlank(jwt.getSubject())) {
                    return JWT.create()
                            .withIssuer(jwt.getIssuer())
                            .withClaim(USER_ID, jwt.getClaim(USER_ID).asString())
                            .withClaim(ROLE, jwt.getClaim(ROLE).asString())
                            .withExpiresAt(jwtBo.getExpiresAt())
                            .withSubject(jwt.getSubject())
                            .withIssuedAt(jwtBo.getIssuedAt())
                            .withJWTId(UUIDUtil.getShortUUid())
                            .sign(Algorithm.HMAC256(jwtBo.getSecret()));
                }
            }
        } catch (UnsupportedEncodingException ignore) {
        }
        return null;
    }
}
