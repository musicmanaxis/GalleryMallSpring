
//스프링이 JpaRepository 인터페이스를 기반으로 자동으로 클래스를 생성해 준다
//스프링이 JpaRepository 인터페이스를 보고, Item 엔티티와 관련된 데이터를 관리하는 클래스를 생성합니다.
//클래스는 데이터베이스와 통신할 수 있는 구체적인 메서드를 포함합니다. findAll, findById, save 등의 메서드가 자동으로 구현
package gallerymallbackend.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository; 

import gallerymallbackend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByMemberId(int memberId);  //멤버에 있는 카트 정보를 리스트형태로 가져옴
    Cart findByMemberIdAndItemId(int memberId, int itemId);  //멤버와 아이템 아이디로 카트 정보를 가져옴
}

