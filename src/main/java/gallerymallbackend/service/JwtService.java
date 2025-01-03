package gallerymallbackend.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(String key, Object value);
    Claims getClaims(String token);  
    boolean isVaild(String token);  //카트컨트롤러에서 사용자가 요청을 할 때 요청한 사용자가 올바른지.. 인자로 받은 토큰이 유효한지 검사 
    int getId(String token);  //토큰을 받아서 id를 반환
}
