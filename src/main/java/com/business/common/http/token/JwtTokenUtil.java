package com.business.common.http.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.business.common.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * JwtToken
 * Created by yuTong on .
 */
public class JwtTokenUtil {
    public static String cretaToken() {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withHeader(new HashMap<String, Object>() {{
                        put("alg", "HS256");
                        put("typ", "JWT");
                    }})
                    .withIssuer("joker")
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String s = JwtTokenUtil.cretaToken();
        System.out.println(s);
        DecodedJWT jwt = JWT.decode(s);
        System.out.println(JsonUtil.objectToJson(jwt));
    }
}
