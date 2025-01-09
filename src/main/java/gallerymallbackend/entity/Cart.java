// 이 클래스는 데이터베이스와 상호작용하여 items 테이블의 데이터를 관리(읽기, 쓰기, 수정, 삭제)하는 역할을 합니다.
//데이터베이스의 레코드를 자바 객체로 변환하여 관리합니다.
//클래스 Item은 데이터베이스의 items 테이블과 매핑, 클래스의 각 필드는 items 테이블의 컬럼으로 매핑

package gallerymallbackend.entity;

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
@Table(name="cart")  
public class Cart {

  @Id  //필드가 Primary Key임을 나타냅니다.
  @GeneratedValue(strategy=GenerationType.IDENTITY) //id 필드가 자동으로 증가되는 값임을 나타냅니다.
  private int id;

  @Column 
  private int memberId; //어떤 사람이 아이템을 담았는지 

  @Column
  private int itemId;  //어떤 아이템을 담았는지

}
