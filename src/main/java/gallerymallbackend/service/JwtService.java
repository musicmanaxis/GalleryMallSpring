package gallerymallbackend.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(String key, Object value);  
    //JWT 설계 관례상  키는 String으로 사용,  값은 Object로 설정하여 유연하게 처리
    //(key:memeberId, value:id)를 받아서 토큰을 반환 id는 실제로 int형이지만 Object로 받음
    //Object를 설정한 이유는 다양한 데이터 타입을 받아들이기 위해서...오토박싱기능이 있으므로 별도의 형변환이 필요없다.
    Claims getClaims(String token);  
    boolean isVaild(String token);  
    //카트컨트롤러에서 사용자가 요청을 할 때 요청한 사용자가 올바른지.. 인자로 받은 토큰이 유효한지 검사 
    int getId(String token);  //토큰을 받아서 id를 반환
}
