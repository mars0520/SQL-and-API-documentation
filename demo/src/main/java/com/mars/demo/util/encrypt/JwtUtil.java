package com.mars.demo.util.encrypt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mars.demo.base.constant.SysConst;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description jwt验证
 * @author xzp
 * @date 2019/10/9 10:35 上午
 **/
public class JwtUtil {

    /**
     * 生成签名，EXPIRE_TIME分钟过期
     *
     * @param **username**
     * @param **password**
     * @return
     */
    public static String sign(String username, String password) {
        try {
            // 设置过期时间
            Date date = new Date( System.currentTimeMillis() + SysConst.EXPIRE_TIME );
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256( SysConst.TOKEN_SECRET );
            // 设置头部信息
            Map<String, Object> header = new HashMap<String, Object>( 2 );
            header.put( "Type", "Jwt" );
            header.put( "alg", "HS256" );
            // 返回token字符串
            return JWT.create()
                    .withHeader( header )
                    .withClaim( "loginName", username )
                    .withClaim( "pwd", password )
                    .withExpiresAt( date)
                    .sign( algorithm );
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param **token**
     * @return
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256( SysConst.TOKEN_SECRET );
            JWTVerifier verifier = JWT.require( algorithm ).build();
            DecodedJWT jwt = verifier.verify( token );
            //System.err.println("jwt:"+jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

