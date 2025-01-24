package gallerymallbackend.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import gallerymallbackend.dto.OrderDto;
import gallerymallbackend.entity.Order;
import gallerymallbackend.repository.CartRepository;
import gallerymallbackend.repository.OrderRepository;
import gallerymallbackend.service.JwtService;
import jakarta.transaction.Transactional;

@RestController 
public class OrderController {

@Autowired 
JwtService jwtService;

@Autowired
OrderRepository orderRepository;

@Autowired
CartRepository cartRepository;
 
@GetMapping("/api/orders") //주문한 것을 보여주는 메서드 
  public ResponseEntity getOrder(@CookieValue(value="token", required=false) String token){
      if(!jwtService.isValid(token)){
        throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
      }
      int memberId=jwtService.getId(token);
      List<Order> orders=orderRepository.findByMemberIdOrderByIdDesc(memberId);
      //최근에 주문한 것을 최상단에 정렬하기 위해 findAll()을 쓰지 않고 대체, Repository에서 가져온 것은 리스트형태로 반환된다.

      return new ResponseEntity<>(orders, HttpStatus.OK);
  }

@Transactional //하단 주석 참조
@PostMapping("/api/orders")  //주문하는 메서드(사용자 주문정보(Json)->Dto->Entity->Repository를 통한 저장)
  public ResponseEntity pushOrder(@RequestBody OrderDto dto, @CookieValue(value="token", required=false) String token){
      if(!jwtService.isValid(token)){
         throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
       }
     
       int memberId=jwtService.getId(token);
       Order newOrder=new Order();  //dto를 order(엔티티)로 저장하기 위해 생성
       newOrder.setMemberId(memberId);
       newOrder.setName(dto.getName());  //사용자가 넘긴 dto에서 엔티티에 저장
       newOrder.setAddress(dto.getAddress());
       newOrder.setPayment(dto.getPayment());
       newOrder.setCardNumber(dto.getCardNumber());
       newOrder.setItems(dto.getItems());
       newOrder.setOrderDate(dto.getOrderDate());  //주문날짜
       System.out.println(newOrder.getOrderDate());//주문 날짜 출력

       orderRepository.save(newOrder);  //#1.변환된 엔티티 객체를 JPA Repository를 사용하여 데이터베이스에 저장
                                        //저장하고 나면(결제를 완료하면) 카트를 비워주는 작업을 한다.
       cartRepository.deleteByMemberId(memberId);  //#2.특정 사용자의 카트를 비우는 것

       //*@Transactional이 필요한 이유는 #1,#2과정이 하나의 트랜잭션으로 묶어 실행, 실패 시 전체 작업을 롤백함으로써 데이터의 일관성과 무결성을 보장



       return new ResponseEntity<>(HttpStatus.OK);
  }

}

// @RequestBody는 Spring Framework에서 사용되는 어노테이션으로, 
// 클라이언트가 요청 본문(request body)에 담아 보낸 데이터를 자바 객체로 변환하여 컨트롤러 메서드의 파라미터로 전달하는 역할
// Spring은 JSON 데이터를 자동으로 OrderDto 객체로 변환