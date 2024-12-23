
package gallerymallbackend.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gallerymallbackend.entity.Member;
import gallerymallbackend.repository.MemberRepository;




@RestController
public class AccountController {

 @Autowired
  MemberRepository memberRepository;

  @PostMapping("/api/account/login")
  public int login(@RequestBody Map<String, String> params) {
    Member member=memberRepository.findByEmailAndPassword(params.get("email"), params.get("password"));
    
    if(member !=null){
      return member.getId();
    }
      return 0;

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