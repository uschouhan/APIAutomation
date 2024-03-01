package com.angelone.tests.orderapi;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.aeonbits.owner.util.Base64.encode;

public class JwtGenerator {

    public static String generateJwtToken(String mobNo, String userId, String secretToken) {
        
        // Build the user data payload
        Map<String, Object> userData = new HashMap<>();
        userData.put("country_code", "");
        userData.put("mob_no", mobNo);
        userData.put("user_id", userId);
        userData.put("source", "SPARK");
        userData.put("app_id", "56567");
        userData.put("created_at", "2023-11-06T09:00:36.266295484Z");
        userData.put("dataCenter", "");

        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + 604800000); // 1 week later

        Algorithm algorithm = Algorithm.HMAC256(secretToken);

        // Build the JWT payload
        JWTCreator.Builder jwtBuilder = JWT.create()
                .withIssuer("angel")
                .withIssuedAt(issuedAt)
                .withExpiresAt(expirationDate)
                .withClaim("userData", userData)
                .withClaim("user_type", "client")
                .withClaim("token_type", "non_trade_access_token")
                .withClaim("source", "SPARK")
                .withClaim("device_id", "4af4fb8f-79fa-5bce-9c8c-9680daae8c5f")
                .withClaim("act", new HashMap<>());
        return jwtBuilder.sign(algorithm);
    }

    public static String generateJWTForTradeToken(String mobNo, String userId, String secretToken) {

        // Build the user data payload
        Map<String, Object> userData = new HashMap<>();
        userData.put("country_code", "");
        userData.put("user_id", userId);
        userData.put("created_at", "2023-11-06T09:00:36.266295484Z");
        userData.put("mob_no", mobNo);
        userData.put("source", "SPARK");
        userData.put("app_id", "56567");

        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + 604800000); // 1 week later

        Algorithm algorithm = Algorithm.HMAC256(secretToken);

        // Build the JWT payload
        JWTCreator.Builder jwtBuilder = JWT.create()
                .withIssuer("angel")
                .withIssuedAt(issuedAt)
                .withExpiresAt(expirationDate)
                .withClaim("userData", userData);
        return jwtBuilder.sign(algorithm);
    }

    public static void main(String[] args) {
        // Example usage
        String mobNo = "9620052526";    // Replace with the actual mobile number
        String userId = "S833402"; // Replace with the actual user ID
        String secretToken = "aAJALJFLJLSLFJ@##@!12123a"; // Replace with your actual secret key

        String jwtToken = generateJwtToken(userId,mobNo,secretToken);
        System.out.println("Generated JWT Token: " + jwtToken);
    }



    public static String GenerateJWT(JSONObject Headers,JSONObject Payload, String secret) {
        String JWT="";
        if(Headers.get("alg").toString().equals("HS256")) {
            String signature = hmacSha256(encode(Headers.toString().getBytes()) + "." + encode(Payload.toString().getBytes()), secret);
            JWT = encode(Headers.toString().getBytes()) + "." + encode(Payload.toString().getBytes()) + "." + signature;
        }else if(Headers.get("alg").toString().equals("HS512")) {
            String signature = hmacSha512(encode(Headers.toString().getBytes()) + "." + encode(Payload.toString().getBytes()), secret);
            JWT = encode(Headers.toString().getBytes()) + "." + encode(Payload.toString().getBytes()) + "." + signature;
        }
        return JWT;
    }

    @SneakyThrows
    private static String hmacSha256(String data, String secret) {
        try {

            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return encode(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            return null;
        }
    }

    private static String hmacSha512(String data, String secret) {
        try {

            byte[] hash = secret.getBytes(StandardCharsets.UTF_8);
            Mac sha512Hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA512");
            sha512Hmac.init(secretKey);

            byte[] signedBytes = sha512Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return encode(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            return null;
        }
    }
}
