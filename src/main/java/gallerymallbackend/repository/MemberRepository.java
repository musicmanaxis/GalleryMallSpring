
package gallerymallbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gallerymallbackend.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
  Member findByEmailAndPassword(String email, String password);
}
//스프링 데이터 JPA의 메서드 이름 규칙에 의해 findByEmailAndPassword()이름을 만든것이다.
//SELECT * FROM members WHERE email = ? AND password = ?로 스프링이 자동으로 쿼리문을 만들어줌
//Member findByEmailAndPassword(String email, String password);는 Member 엔터티에서 email과 password 필드를 조건으로 조회하겠다는 선언입니다.