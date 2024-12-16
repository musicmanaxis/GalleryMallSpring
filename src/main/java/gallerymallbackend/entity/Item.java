package gallerymallbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name="items")  //디비테이블과 클래스를 연결
public class Item {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)  //지동증가값임을 추가
  private int id;

  @Column(length=255, nullable=false)
  private String name;
}
