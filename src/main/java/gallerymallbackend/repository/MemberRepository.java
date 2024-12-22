
package gallerymallbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gallerymallbackend.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
  Member findByEmailAndPassword(String email, String password);
}
