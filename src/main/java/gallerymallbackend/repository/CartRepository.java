package gallerymallbackend.repository;

import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;

import gallerymallbackend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
 List<Cart> findByMemberId(int memberId);               //멤버id로 카트정보를 리스트형태로 가져옴(같은 멤버id라도 아이템이 다르면 카트가 여러개일 수 있음)
 
 Cart findByMemberIdAndItemId(int memberId, int itemId);//멤버id와 아이템id로 특정 카트정보를 가져옴

 void deleteByMemberId(int memberId);  //카트를 비우는 작업
}

