// 소프트웨어 개발에서 자주 쓰이는 JWT(Json Web Token)는 다음과 같은 구조로 구성됩니다:
// Header: 토큰의 타입(JWT)과 서명 알고리즘(예: HS256).
// Payload: 사용자 정보나 데이터를 포함.
// Signature: 위조 방지를 위해 생성된 서명.

package gallerymallbackend.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;


public class JwtServiceImpl implements JwtService {

    private String secretKey ="abbci2ioadij@@@ai17a662###8139!!!18ausudahd178316738687687@@ad6g";  
    //임의로 작성한 비밀키...이거 너무 짧아도 안되고 너무 길어도 안된다. 이건 예시이니까 실제로 사용하면 안된다.

    @Override
    public String getToken(String key, Object value) {

        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30);
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put(key, value);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

}