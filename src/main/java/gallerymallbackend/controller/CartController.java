
package gallerymallbackend.controller;
import java.util.List;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import gallerymallbackend.entity.Cart;
import gallerymallbackend.entity.Item;
import gallerymallbackend.repository.CartRepository;
import gallerymallbackend.service.JwtService;

import org.springframework.web.bind.annotation.GetMapping;
import gallerymallbackend.repository.ItemRepository;



@RestController 
public class CartController {

@Autowired 
JwtService jwtService;

@Autowired 
CartRepository cartRepository;

@Autowired
ItemRepository itemRepository;

@GetMapping("/api/cart/items")   //특정 사용자의 장바구니 아이템을 조회하는 메서드
public ResponseEntity getCartItems(@CookieValue(value="token", required=false) String token){
  if(!jwtService.isVaild(token)){  //토큰이 유효하지 않으면
    throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);  //UNAUTHORIZED(401) 상태 코드를 반환
  }
  int memberId=jwtService.getId(token);
  List<Cart> cartList=cartRepository.findByMemberId(memberId);  //멤버아이디값으로 카드정보를 가져와서
  List<Integer> itemIdList=cartList.stream().map(Cart::getItemId).toList();  //Cart 엔티티의 itemId를 리스트로 변환
  List<Item> items=itemRepository.findByIdIn(itemIdList);  //ItemRepository에서 itemIdList에 있는 아이템을 찾아서 반환

  return new ResponseEntity<>(items, HttpStatus.OK);
}


@PostMapping("/api/cart/items/{ItemId}") //{ItemId}는 변수로 받아들이는 것이다.
  public ResponseEntity pushCartItem(  //장바구니에 아이템을 담는 메서드
    @PathVariable("itemId") int itemId, 
    @CookieValue(value="token", required=false) String token
    ){
      if(!jwtService.isVaild(token)){
         throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
       }
       int memberId=jwtService.getId(token); 
       Cart cart= cartRepository.findByMemberIdAndItemId(memberId, itemId);   
       
       
       if(cart==null){   
         cart=new Cart();   //특정 사용자의 카트를 새로 만드는것
         cart.setMemberId(memberId);
         cart.setItemId(itemId);
         cartRepository.save(cart);
       }

       return new ResponseEntity<>(HttpStatus.OK);
  }
}