package com.tb.commpt.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tb.commpt.constant.ConsCommon;
import com.tb.commpt.global.SystemConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * Created by Tanbo on 2017/9/4.
 * <p>
 * <p>
 * 第一部分为头部（header),
 * {
 * 'typ': 'JWT',
 * 'alg': 'HS256'
 * }将头部进行base64加密
 * 第二部分为载荷（payload)，存放有效信息并且进行base64加密
 * <p>
 * <p>
 * <p>
 * 第三部分是签证（signature)，包含三部分：header (base64后的)+payload (base64后的)+secret
 */
public class JwtUtil {

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {
        String stringKey = systemConfig.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * <p>
     * iss: jwt签发者
     * sub: jwt所面向的用户
     * aud: 接收jwt的一方
     * exp: jwt的过期时间，这个过期时间必须要大于签发时间
     * nbf: 定义在什么时间之前，该jwt都是不可用的.
     * iat: jwt的签发时间
     * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     *
     * @param id
     * @param subject   信息的json字符串
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 生成subject信息
     *
     * @param object
     * @return
     */
    public static String generalSubject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}
