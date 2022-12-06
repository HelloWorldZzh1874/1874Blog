package com.zzh.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.zzh.common.constant.RedisConstant.LOGIN_TOKEN;

/**
 * @author zzh
 * @description TODO
 * @date 2022/1/2520:12
 */

public class JwtUtil {

    /**
     * token过期时间
     */
    @Value("${token.expireTime}")
    private static long expireTime;

    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定JWT_SEC秘钥
     *
     * @param jwtSec    jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param uuid  token持有者 可根据需要传递的信息添加更多, 因为浏览器get传参url限制，不建议放置过多的参数
     */
    public static String createJwt(String jwtSec, String uuid) {
        // 指定签名的时候使用的签名算法，也就是header那部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建payload的私有声明
        Map<String, Object> claims = new HashMap<String, Object>();
        // 在token主体中添加uuid键值对
        claims.put(LOGIN_TOKEN, uuid);
        // 设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(uuid)
                // iat: jwt的签发时间
                .setIssuedAt(now)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, jwtSec.getBytes(StandardCharsets.UTF_8));
        return builder.compact();
    }



    /**
     * Token的解密
     *
     * @param jwtSec jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param token  加密后的token
     */
    public static Claims parseJwt(String jwtSec, String token) {
        // 得到DefaultJwtParser
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(jwtSec.getBytes(StandardCharsets.UTF_8))
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
    }
}
