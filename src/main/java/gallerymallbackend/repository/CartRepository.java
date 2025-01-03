package gallerymallbackend.repository;

import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository; 
import gallerymallbackend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByMemberId(int memberId);  //멤버에 있는 카트 정보를 리스트형태로 가져옴
    Cart findByMemberIdAndItemId(int memberId, int itemId);  //멤버와 아이템 아이디로 카트 정보를 가져옴
}

