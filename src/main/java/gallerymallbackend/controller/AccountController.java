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
//이 클래스는 사용자가 로그인하면 JWT 토큰을 생성하여 클라이언트에게 쿠키로 전달하고(login 메서드), 
//이후 요청 시 클라이언트가 보낸 쿠키의 토큰을 검증하는 역할(check 메서드)을 합니다.
// HttpServletResponse역할 :클라이언트에게 데이터 전달, 응답 상태를 설정, 쿠키를 추가, HTTP 헤더 설정 등

@RestController
public class AccountController {

@Autowired
  JwtService jwtService;

 @Autowired 
  MemberRepository memberRepository;

//@PostMapping:서버에 데이터를 생성하거나 전송하기 위한 처리. @GetMapping은 데이터를 가져오기 위한 처리
//@RequestBody로 스프링이 params로 Java Map 객체생성에만 관여.
//컨트롤러 메서드에 HttpServletResponse를 매개변수로 선언하면 스프링이 객체를 자동으로 전달, 이를 사용해 응답상태 코드, 헤더, 쿠키 등을 설정할 수 있다.
//ResponseEntity는 Spring 제공하는 클래스로, HTTP 응답 상태 코드와 함께 응답 본문을 포함할 수 있다.
//*HttpServletResponse는 쿠키를 응답헤더에 추가시키고 ResponseEntity는 쿠키가 추가된 헤더와 본문을 사용자에게 전송한다.

  @PostMapping("/api/account/login")//*email과 password를 받아서 member테이블에서 id를 찾아서 쿠키와 id값을 반환하는 메서드
  public ResponseEntity login(@RequestBody Map<String, String> params, HttpServletResponse res){  
    //Map<key, value>구조->디비 테이블 컬럼과 유사한 구조로 정의하면 |email(키)   |erlia@naver.com(값) 
    //                                                    |password(키)|5291(값)            이런식으로 2줄이 들어간다.
    //HttpServletResponse를 메서드의 매개변수로 선언하면, 스프링이 이를 객체로 생성주입함
    
    //#1.넘어온 params로 이메일과 비번으로 레포지토리에서 조회하여 멤버객체를 받아온다.
    Member member=memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
    
     if(member !=null){
      int id= member.getId();  
   //#2.member객체를 이용해서 id를 뽑고 id를 사용해서 jwtService의 인증과정을 거친다.
      String token1=jwtService.getToken("id", id);  //1.member id값으로 토큰을 얻어낸다.
      Cookie cookie=new Cookie("token", token1);   //*2.토큰을 쿠키에 담는다. 이때 만들어진 토큰의 이름이 나중에 다시 사용할 이름이다.
      cookie.setHttpOnly(true);                //자바스크립트로는 접근할수 없도록 막아준다.
      cookie.setPath("/");                          //3.쿠키가 애플리케이션의 모든 경로에서 사용될 수 있도록 설정합니다.
      res.addCookie(cookie);                            //*4.res가 쿠키를 응답헤더에 추가, 
                                                        //*res를 별도로 return하지 않아도 ResponseEntity가 응답헤더와 본문을을 전송처리

      return new ResponseEntity<>(id, HttpStatus.OK);  //로그인 성공시 200 OK와 함께 사용자의 id를 반환
      // ResponseEntity는 매개배변수의 순서가 바뀌면 안된다, 첫번째 매개변수 타입만 보고 <>안에 들어가는 타입을 결정한다.
      //<>는 첫 번째 인자를 기반으로 제네릭 타입(T)을 추론하도록 컴파일러에 지시
      }//IF문 종료
      
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);   //클라이언트에 HTTP 404 응답전송. 
    } 

    // 로그인 메서드 전체 흐름 요약
    // #1.사용자 인증: 이메일과 비밀번호를 통해 데이터베이스에서 Member 객체를 조회.
    // #2.JWT 생성: Member 객체에서 ID를 추출하고, 이 ID를 기반으로 JWT 토큰 생성.
    // #3.JWT 전달:생성된 JWT 토큰을 쿠키에 담아 클라이언트에 전달.
    // ***이후 클라이언트는 이 쿠키를 서버로 다시 보내면서 인증 상태를 유지.

  @PostMapping("/api/account/logout")  //Header.vue에 로그아웃 처리가 있음
  public ResponseEntity logout(HttpServletResponse res){ 
    Cookie cookie=new Cookie("token", null); //서버가 브라우저의 인증 쿠키(예: "token")를 삭제하도록 명령.
    cookie.setPath("/");                            //쿠키가 도메인의 모든 경로에서 사용 가능하도록 설정
    cookie.setMaxAge(0);                         //쿠키의 유효기간을 0으로 설정하여 즉시 만료시킴
    res.addCookie(cookie);                              //응답 헤더를 통해 브라우저가 쿠키를 삭제하도록 요청

    return  new ResponseEntity<>("로그아웃이 처리되었습니다.", HttpStatus.OK);
     
  }
      
  @GetMapping("/api/account/check")  //**check는 뷰 최상단 컴포넌트 App.vue에서 실행
  public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
    //required = false를 사용하면 쿠키가 없을 때도 예외가 발생하지 않고 null로 처리할 수 있어서 편리, 예외처리가 불필요
    //*쿠키안에 토큰이 있고 토큰안에 클레임스가 있다.
    //*login메서드에서 토큰의 이름을 "token"으로 지정했기 때문에 @CookieValue(value = "token")으로 토큰을 받아올 수 있다.

    Claims claims = jwtService.getClaims(token);  //#1.토큰의 유효성을 검증
    if (claims != null) {
      int id = Integer.parseInt(claims.get("id").toString());  //#2(id값추출).클레임스 안에 payload가 제이슨형태 id(key)의 값이 object라서 toString()필요
      return new ResponseEntity<>(id, HttpStatus.OK);
      //** JWT에서는 #1,2 두 과정의 인증과정의 유효성과정으로 확인 동일인임을 확인한다. 
    }
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
    
}
//브라우저가 특정 도메인에 대한 요청을 할 때, 해당 도메인에 관련된 쿠키를 자동으로 포함시켜 보내는 것이 기본 동작
//api/account/check 요청을 보낼 때, 브라우저는 이전에 받은 token 쿠키를 자동으로 헤더에 포함시켜 서버로 전송
//@CookieValue의 역할: @CookieValue(value = "token", required = false)는 클라이언트가 보낸 token이라는 이름의 쿠키를 메서드 인자로 자동으로 전달


//Map은 항상 <Key, Value>의 형태로 두 개의 타입 파라미터를 필요로 합니다.
//params에는 2개의 key와 value 쌍이 존재하게 됩니다.
//params = {
//   "email": "user@example.com",
//   "password": "password123"
// };