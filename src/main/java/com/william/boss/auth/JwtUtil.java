package com.william.boss.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.william.boss.constant.CommonConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.joda.time.DateTime;

import java.security.Key;
import java.util.Date;

/**
 * @author john
 */
public class JwtUtil {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(JwtInfo jwtInfo, int expire) {

        return Jwts.builder()
                .setSubject(jwtInfo.getUsername())
                .claim(CommonConstants.JWT_KEY_USER, jwtInfo)
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(KEY)
                .compact();
    }

    /**
     * 获取token中的用户信息
     * @param token token
     * @return 用户信息
     */
    public static JwtInfo getInfoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY).build().parseClaimsJws(token).getBody().get(CommonConstants.JWT_KEY_USER, JwtInfo.class);
    }

    /**
     * 获取token过期时间
     * @param token token
     * @return token过期时间
     */
    public static Date getTokenExpire(String token) {
        return Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token).getBody().getExpiration();
    }

    public static void main(String[] args) {
        System.out.println(Keys.secretKeyFor(SignatureAlgorithm.HS256));

        JwtInfo jwtInfo = new JwtInfo("test");

        String jws = generateToken(jwtInfo, 7200);

        System.out.println(jws);

        System.out.println(getInfoFromToken(jws));

        System.out.println(getTokenExpire(jws));
    }

}
