package gallerymallbackend.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import gallerymallbackend.entity.Cart;
import gallerymallbackend.entity.Item;
import gallerymallbackend.repository.CartRepository;
import gallerymallbackend.repository.ItemRepository;
import gallerymallbackend.service.JwtService;

@RestController 
public class CartController {

@Autowired 
JwtService jwtService;

@Autowired 
CartRepository cartRepository;

@Autowired
ItemRepository itemRepository;

@GetMapping("/api/cart/items")   //특정 사용자 장바구니의 아이템을 조회하는 메서드
public ResponseEntity getCartItems(@CookieValue(value="token", required=false) String token){
  //@CookieValue: 클라이언트가 요청 시 함께 전송한 쿠키의 값을 읽어와 메서드 파라미터에 할당
  //클라이언트가 보낸 쿠키안에서 이름이 "token"인 값을 읽어와 token 변수에 할당
  if(!jwtService.isVaild(token)){  //토큰이 유효하지 않으면
    throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);  //UNAUTHORIZED(401) 상태 코드를 반환
  }
  int memberId=jwtService.getId(token);

  //헌재 디비에서 cart테이블에는 memberId와 itemId가 저장되어 있으므로 memberId로 아이템을 찾아야 한다.
  List<Cart> cartList=cartRepository.findByMemberId(memberId);  //멤버아이디값으로 카트정보를 가져와서
  List<Integer> itemIdList=cartList.stream().map(Cart::getItemId).toList();  //cartList에 있는 Cart 객체에서 itemId 값만 추출하여 새로운 List<Integer> 객체로 변환
  List<Item> items=itemRepository.findByIdIn(itemIdList);  //ItemRepository에서 itemIdList에 있는 아이템을 찾아서 반환


  //Cart::getItemId는 Cart클래스의 getItemId메서드를 사용
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