
package gallerymallbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gallerymallbackend.entity.Member;
//➡️ MemberRepository는 인터페이스이지만 스프링이 자동으로 인터페이스를 구현하여 클래스를 만들고 그 구현클래스에서 다른 클래스에 @Autowired으로 객체를 끌어다 쓴다
//➡️ Repository만 인터페이스로 작성하는 이유는 Spring Data JPA가 자동으로 구현 클래스를 생성해주기 때문이다.
//➡️ 나머지 Controller, Service, Entity, DTO 등은 직접 로직을 작성해야 하므로 클래스로 만든다.
public interface MemberRepository extends JpaRepository<Member, Integer> {
  Member findByEmailAndPassword(String email, String password);
}

//스프링 데이터 JPA의 메서드 이름 규칙에 의해 findByEmailAndPassword()이름을 만든것이다.
//SELECT * FROM members WHERE email = ? AND password = ?로 스프링이 자동으로 쿼리문을 만들어줌
//Member findByEmailAndPassword(String email, String password);는 Member 엔터티에서 email과 password 필드를 조건으로 조회하겠다는 선언입니다.