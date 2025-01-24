// 데이터베이스의 orders 테이블과 매핑되는 객체, 주문 정보를 처리하기 위한 데이터 모델로 사용
package gallerymallbackend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name="orders")  
public class Order {

  @Id  //필드가 Primary Key임을 나타냅니다.
  @GeneratedValue(strategy=GenerationType.IDENTITY) //id 필드가 자동으로 증가되는 값임을 나타냅니다.
  private int id;

  @Column 
  private int memberId; //어떤 사람이 아이템을 담았는지 

  @Column(length=50, nullable = false)
  private String name;  

  @Column(length=500, nullable = false)
  private String address;  

  @Column(length=10, nullable = false)
  private String payment;  

  @Column(length=16)
  private String cardNumber;  

  @Column(length=500, nullable = false)
  private String items;  

  @Column(columnDefinition = "DATETIME") // ✅ DATETIME 컬럼으로 지정
    private LocalDateTime orderDate;


}

