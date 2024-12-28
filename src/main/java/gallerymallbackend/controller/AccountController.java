
// *1.쿠키로 JWT 토큰을 전달해 인증 정보를 유지하고, *2.응답 본문으로 사용자 ID를 반환해 프론트엔드에서 사용자 관련 데이터를 처리
package gallerymallbackend.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import gallerymallbackend.entity.Member;
import gallerymallbackend.repository.MemberRepository;
import gallerymallbackend.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


// HttpServletResponse역할 :클라이언트에게 데이터 전달, 응답 상태를 설정, 쿠키를 추가, HTTP 헤더 설정 등

@RestController
public class AccountController {

@Autowired
  JwtService jwtService;
 @Autowired
  MemberRepository memberRepository;

//@PostMapping:서버에 데이터를 생성하거나 전송하기 위한 처리. @GetMapping은 데이터 조회
  @PostMapping("/api/account/login")
  public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res){ 
    //매개변수에 객체를 나열하면 Spring이  객체를 생성하고 자동으로 전달.  Map 객체와 HttpServletResponse 객체 모두 Spring이 생성하여 메서드에 전달
    //ResponseEntity는 Spring Framework에서 제공하는 클래스로, HTTP 응답 상태 코드와 함께 응답 본문을 포함할 수 있다.

    Member member=memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
    
     if(member !=null){
      int id= member.getId();  //로그인 성공시 vue에게 member의 id를 반환, Login.vue 참조
      
      String token1=jwtService.getToken("id", id);  //id값으로 토큰 변환
      Cookie cookie=new Cookie("token", token1);  //토큰을 쿠키에 담아서 클라이언트에게 전달
      cookie.setHttpOnly(true);  //자바스크립트로는 접근할수 없도록 막아준다.
      cookie.setPath("/"); //쿠키가 애플리케이션의 모든 경로에서 사용될 수 있도록 설정합니다.
      res.addCookie(cookie);  //*1.쿠키를 HttpServletResponse 객체를 통해 클라이언트에게 전달

      return new  ResponseEntity<>(id, HttpStatus.OK);  //*2.로그인 성공시 200 OK와 함께 사용자의 id를 반환
      // ResponseEntity는 매개배변수의 순서가 바뀌면 안된다, 첫번째 매개변수 타입만 보고 <>안에 들어가는 타입을 결정한다.
     }
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);  //이 코드를 던지면 클라이언트는 HTTP 404 응답을 받게 됩니다. 
    } 

   @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        Claims claims = jwtService.getClaims(token);

        if (claims != null) {
            int id = Integer.parseInt(claims.get("id").toString());
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
  }

//@RequestBody: 클라이언트가 HTTP 요청 본문(body)에 담아 보낸 데이터를 Java 객체로 변환해주는 역할, email과 password
//@RequestBody는 JSON 데이터를 Map<String, String> 객체로 변환합니다.
//(@RequestBody Map<String, String> params): 요청 본문의 데이터를 Map<String, String> 형태로 변환하여  params라는 이름의 변수에 할당하겠다는 것을 의미

//Map은 항상 <Key, Value>의 형태로 두 개의 타입 파라미터를 필요로 합니다.
//params에는 2개의 key와 value 쌍이 존재하게 됩니다.
//params = {
//   "email": "user@example.com",
//   "password": "password123"
// };