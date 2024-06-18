package xmut.cs.ojbackend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import xmut.cs.ojbackend.entity.User;

import java.util.Date;

public class JwtUtil {
    private static final Algorithm algorithm = Algorithm.HMAC256("2dozl28%$%3=b3()ndka9duf==*");

    static public String generateToken(User user) {
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000 * 24 * 10;//24小时
        Date end = new Date(currentTime);
        return JWT.create()
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withIssuedAt(start)
                .withExpiresAt(end)
                .sign(algorithm);
    }

    static public Object parseToken(String token, String key) {
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        return jwtVerifier.verify(token).getClaim(key).as(Object.class);
    }

    static public Integer getUserId(String token) {
        return (Integer) parseToken(token, "id");
    }

    static public String getUsername(String token) {
        return (String) parseToken(token, "username");
    }


    static public Boolean verifyToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}