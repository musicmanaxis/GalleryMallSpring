//스프링에서 사용자 인증을 처리하는 방법으로 JWT 방식과 세션 ID 방식이 일반적으로 사용
//여기서는 jwt방식을 사용한다.
package gallerymallbackend.service;
import io.jsonwebtoken.Claims;
//1번 메서드 설명
//JWT 설계 관례상 키는 String으로 사용, 값은 Object로 설정하여 유연하게 처리
//(key:memeberId, value:id)를 받아서 토큰을 반환 id는 실제로 int형이지만 Object로 받음
//Object를 설정한 이유는 다양한 데이터 타입을 받아들이기 위해서...오토박싱기능이 있으므로 별도의 형변환이 필요없다.

public interface JwtService {
//JwtService에서 4개의 메서드를 설정한다.
//1.토큰을 생성하는 메서드   
    String getToken(String key, Object value); 
//2.토큰을 이용하여 클레임을 얻은 메서드 
    Claims getClaims(String token);  
//3.토큰이 유효한지 검사하는 메서드(카트컨트롤러에서 사용자가 요청을 할 때 요청한 사용자가 올바른지.. 인자로 받은 토큰이 유효한지 검사)
    boolean isValid(String token);   
//4.토큰을 이용하여 id를 얻는 메서드 
    int getId(String token);  
}
