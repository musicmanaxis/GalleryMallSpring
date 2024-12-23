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

@Getter//클래스의 필드에 대해 getter메서드(getId(), getName())를 자동으로 생성해주는 역할(Lombok 라이브러리에서 제공), 코드중복제거
@Entity//해당 클래스가 데이터베이스 테이블과 매핑되는 엔티티(Entity)임,  JPA를 통해 데이터베이스의 테이블과 연동되어 관리됨
@Table(name="items")  //엔티티가 매핑될 데이터베이스 테이블 이름을 지정.
public class Item {

  @Id  //필드가 Primary Key임을 나타냅니다.
  @GeneratedValue(strategy=GenerationType.IDENTITY) //id 필드가 자동으로 증가되는 값임을 나타냅니다.
  private int id;

  @Column(length=255, nullable=false) //특정 필드가 매핑될 데이터베이스 컬럼의 이름, 길이, nullable 여부 등을 지정
  private String name;

  @Column(length=255)
  private String imgPath;  //실제 DB컬럼 이름중간에 _가 붙어있어도(img_Path) 이런식으로 해도 매핑이 된다.

  @Column
  private int price;

  @Column
  private int discountPrice;

}
