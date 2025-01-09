package gallerymallbackend.controller;
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
import gallerymallbackend.repository.OrderRepository;
import gallerymallbackend.service.JwtService;

@RestController 
public class OrderController {

@Autowired 
JwtService jwtService;

@Autowired
OrderRepository orderRepository;
 
@GetMapping("/api/orders") 
  public ResponseEntity getOrder(  //주문한 것을 보여주는 메서드 
     @CookieValue(value="token", required=false) String token
    ){
      if(!jwtService.isVaild(token)){
        throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
      }

      List<Order> orders=orderRepository.findAll();
      return new ResponseEntity<>(orders, HttpStatus.OK);

    }
    



@PostMapping("/api/orders") 
  public ResponseEntity pushOrder(  //주문하는 메서드
    @RequestBody OrderDto dto,
    @CookieValue(value="token", required=false) String token
    ){
      if(!jwtService.isVaild(token)){
         throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
       }
     
       Order newOrder=new Order();
       newOrder.setMemberId(jwtService.getId(token));
       newOrder.setName(dto.getName());
       newOrder.setAddress(dto.getAddress());
       newOrder.setPayment(dto.getPayment());
       newOrder.setCardNumber(dto.getCardNumber());
       newOrder.setItems(dto.getItems());

       orderRepository.save(newOrder);

       return new ResponseEntity<>(HttpStatus.OK);
  }

}