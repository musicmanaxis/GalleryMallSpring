package gallerymallbackend.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
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

 //특정 사용자 카트의 아이템을 조회하는 메서드에서는 사용자가 보낸 쿠키값으로 처리한다.
@GetMapping("/api/cart/items")  
public ResponseEntity getCartItems(@CookieValue(value="token", required=false) String token){
  //@CookieValue: 클라이언트가 요청 시 함께 전송한 쿠키의 값을 읽어와 메서드 파라미터에 할당
  //클라이언트가 보낸 쿠키안에서 이름이 "token"인 값을 읽어와 token 변수에 할당
  if(!jwtService.isValid(token)){  //토큰이 유효하지 않으면
    throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);  //UNAUTHORIZED(401) 상태 코드를 반환
  }
  int memberId=jwtService.getId(token);

  List<Cart> cartList=cartRepository.findByMemberId(memberId);               //1
  List<Integer> itemIdList=cartList.stream().map(Cart::getItemId).toList();  //2
  List<Item> items=itemRepository.findByIdIn(itemIdList);                    //3 

//1.헌재 디비에서 cart테이블에는 memberId와 itemId가 저장되어 있으므로 memberId로 아이템을 찾아야 한다.
//2.동일한 멤버아이디값으로 여러개일 수 있는 카트객체들을 찾아서 리스트로 반환,
// *여러개의 cart가 필요한 이유는 한명의 사용자가 여러개의 아이템을 장바구니에 담을 수 있기 때문이다. 
//  동일한 memberId이더라도 여러개의 itemId로 cart객체를 여러개 만듦.
//  cartList에 있는 각 Cart 객체에서 itemId 값만 추출하여 새로운 List<Integer> 객체로 변환, toList()는 스트림의 최종연산함수
//3.ItemRepository에서 itemIdList에 있는 아이템을 찾아서 반환, 
//  List<Item> findByIdIn(List<Integer> ids)라고고 itemRepository에 정의되어 있음

  return new ResponseEntity<>(items, HttpStatus.OK);
}

//**카트에 아이쳄을 담거나 아이쳄을 삭제하는 방식은 
//넘어온 itemId와 토큰을 이용하여 멤머id를 얻어 카트에 아이템을 추가하거나 삭제하는 방식으로 쓰인다.

@PostMapping("/api/cart/items/{itemId}") //{ItemId}는 변수로 받아들이는 것이다.
  public ResponseEntity pushCartItem(  //장바구니에 아이템을 담는 메서드
    @PathVariable("itemId") int itemId, @CookieValue(value="token", required=false) String token){
      if(!jwtService.isValid(token)){
         throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
       }
       int memberId=jwtService.getId(token); 
       Cart cart= cartRepository.findByMemberIdAndItemId(memberId, itemId);   
       //해당 사용자의 장바구니에 특정 상품{ItemId}이 이미 존재하는지 확인하는 코드
       
       if(cart==null){   
         cart=new Cart();   //특정 사용자의 카트를 새로 만드는것
         cart.setMemberId(memberId);
         cart.setItemId(itemId);
         cartRepository.save(cart);  
        //JpaRepository가 제공하는 save()는 Cart 객체를 새로운 데이터일 경우 INSERT SQL 실행. 기존 데이터일 경우 UPDATE SQL 실행.
       }
       return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/api/cart/items/{itemId}") //토큰을 통해 memberId와 넘어온 변수 itemId로 cart객체를 찾아서 cart객체를 삭제하는 방식
  public ResponseEntity removeCartItem(@PathVariable("itemId") int itemId, @CookieValue(value="token", required=false) String token){
      if(!jwtService.isValid(token)){  //토큰이 유효하지 않으면
        throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);  //UNAUTHORIZED(401) 상태 코드를 반환
      }
      int memberId=jwtService.getId(token); 
      Cart cart= cartRepository.findByMemberIdAndItemId(memberId, itemId);   

      cartRepository.delete(cart);  //JpaRepository가 제공하는 delete()는 Cart 객체를 데이터베이스에서 삭제하는 기능을 수행합니다.
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }