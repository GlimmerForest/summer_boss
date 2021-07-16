package com.william.boss.auth;

import com.william.boss.constant.CommonConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.lang.Maps;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.joda.time.DateTime;

import java.security.Key;
import java.util.Date;

/**
 * @author john
 */
public class JwtUtil {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 生成token
     * @param jwtInfo jwt payload 信息
     * @param expire 过期时间, 单位秒
     * @return jwt token
     */
    public static String generateToken(JwtInfo jwtInfo, int expire) {
        return Jwts.builder()
                .serializeToJsonWith(new JacksonSerializer<>())
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
    @SuppressWarnings("unchecked,rawtypes")
    public static JwtInfo getInfoFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return Jwts.parserBuilder()
                .deserializeJsonWith(new JacksonDeserializer(Maps.of(CommonConstants.JWT_KEY_USER, JwtInfo.class).build()))
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

    public static String refreshToken(String token) {
        return JwtUtil.generateToken(JwtUtil.getInfoFromToken(token), 7200);

    }

    public static void main(String[] args) {
        System.out.println(Keys.secretKeyFor(SignatureAlgorithm.HS256));

        JwtInfo jwtInfo = new JwtInfo("test");

        String jws = generateToken(jwtInfo, 7200);

        System.out.println(jws);

        Object obj = getInfoFromToken(jws);

        System.out.println(obj);

        System.out.println(getTokenExpire(jws));
    }

}
