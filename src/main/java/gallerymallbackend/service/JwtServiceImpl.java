// 소프트웨어 개발에서 자주 쓰이는 JWT(Json Web Token)는 다음과 같은 구조로 구성됩니다:
// Header: 토큰의 타입(JWT)과 서명으로 어떤 알고리즘했는지(예: HS256).
// Payload(내용):내용의 각 부분을 claim이라고 함, 사용자 정보나 데이터를 포함. 
// Signature: 위조 방지를 위해 생성된 서명.
//사용자의 id를 토큰으로 변환하는 코드입니다.

package gallerymallbackend.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

//@Service을 붙이면, Spring은 해당 클래스 인스턴스를 Spring 컨테이너에 빈으로 등록 즉, 다른 클래스에서 @Autowired로 주입할 수 있다.
@Service("jwtService")
public class JwtServiceImpl implements JwtService {  //AccountController에서 사용함

    private String secretKey ="abbci2ioadij@@@ai17a662###8139!!!18ausudahd178316738687687@@ad6g";  
    //임의로 작성한 비밀키...이거 너무 짧아도 안되고 너무 길어도 안된다. 이건 예시이니까 실제로 사용하면 안된다.

    @Override
    public String getToken(String key, Object value) {  
    //사용자 정보를 입력받은 키("id")와 값(int id)을 바탕으로 JWT를 생성

        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30); //**로그인 시간을 설정 30분
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);//문자열을 바이트 배열로 변환
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());//암호화 키 생성

        Map<String, Object> headerMap = new HashMap<>(); //*1.헤더 설정:Object가 여기서는 String으로 변환되어 사용됨
        headerMap.put("typ", "JWT");  //토큰의 타입
        headerMap.put("alg", "HS256"); //서명 알고리즘
        //JWT의 헤더(Header)에 메타데이터를 설정하는 작업

        Map<String, Object> map = new HashMap<>();//*2.payload 설정:Obejct지만 int형으로 들어옴(클레임 데이터 설정)
        map.put(key, value);   
        //*1번과 2번 객체에서 2번째 인자에 Object를 사용함으로서 다양한 데이터 타입이 사용되어 지는 것을 확인할 수 있다.

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)//헤더 설정
                .setClaims(map)//map이 클레임으로 설정
                .setExpiration(expTime)//만료 시간 설정
                .signWith(signKey, SignatureAlgorithm.HS256);//서명 설정

        return builder.compact();
    }

    @Override
    public Claims getClaims(String token) {  //제공된 토큰을 검증하고 클레임 정보를 추출
        if (token != null && !"".equals(token)) { //토큰이 null이 아니고 빈 문자열이 아닐 때
            try {
                byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
                Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
                return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
                //parseClaimsJws(token).getBody()를 통해 토큰의 Payload를 파싱하여 Claims 객체로 반환
            } catch (ExpiredJwtException e) {
                // 만료됨
            } catch (JwtException e) {
                // 유효하지 않음
            }
        }

        return null;
    }

    @Override
    public boolean isValid(String token) {  //토큰안에 클레임이 있는지 검사
        return this.getClaims(token) != null;
    }

    @Override
    public int getId(String token) {  //토큰을 받아서 토큰을 열어 클레임을 꺼내 클레임에서 int형인 id를 반환하는 메서드
        Claims claims = this.getClaims(token);  //this는 JwtServiceImpl 클래스의 인스턴스

        if (claims != null) {
            return Integer.parseInt(claims.get("id").toString());
            //claims.get("id")는 Object 타입이므로 toString()을 이용하여 String으로 변환한 뒤 int로 변환
            //(Integer) claims.get("id") 같은 형변환이 가능하지만, 안전하지 않을 수 있다.
            //클레임 값이 다른 타입(예: String, Long)이라면 ClassCastException**이 발생할 위험
        }
        return 0;
    }
}

//클레임이란?
// JWT는 헤더(Header), 페이로드(Payload), **서명(Signature)**의 세 부분으로 구성되는데, 
// 그 중 페이로드(Payload) 부분에 클레임이 들어갑니다.

// 클레임은 토큰에 포함된 정보로, JWT를 통해 전달하려는 데이터를 나타냅니다. 
// 예를 들어, 사용자의 ID, 권한 정보, 토큰의 만료 시간 등이 클레임으로 포함될 수 있습니다.
//JWT는 클레임을 사용하여 사용자 정보, 인증 상태, 권한 정보 등을 안전하게 전달하고 검증
//쿠키안에 토큰이 있고 토큰안에 클레임스가 있다