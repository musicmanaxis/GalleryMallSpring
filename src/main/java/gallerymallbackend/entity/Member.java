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
@Table(name="members") 
public class Member {

  @Id 
  @GeneratedValue(strategy=GenerationType.IDENTITY)  
  private int id;

  @Column(length=255, nullable=false, unique=true)
  private String email;

  @Column(length=255, nullable=false)
  private String password;  

 

}
