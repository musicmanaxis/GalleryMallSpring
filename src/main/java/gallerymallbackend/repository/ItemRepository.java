//이 클래스는 데이터베이스를 다루는(조회, 삽입, 수정, 삭제) 구체적인 메서드를 포함. 
//JpaRepository가 제공하는 findAll, findById, save 등의 메서드가 기본으로 있어서 별도로 작성할 필요가 없다.
package gallerymallbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gallerymallbackend.entity.Item;


public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByIdIn(List<Integer> itemIdList);  
    //사용자가 정의한 메서드 jpa명명규칙에 따라 작성된 것임, SQL의 IN 연산자를 사용
}

// JpaRepository는 지정된 클래스(Item클래스)에 @Entity와 @Id가 올바르게 설정되어 있는지 확인하고,
// 자동으로 CRUD에 필요한 메서드를 제공해주는 역할을 합니다.

//JpaRepository<Item, Integer>에서 Item은 관리 대상 엔티티 클래스입니다.
//이 Repository는 Item 엔티티와 연결된 데이터베이스 테이블(items)에 대해 CRUD 작업을 수행합니다.

// ✅ JpaRepository<Item, Integer>에서:
//    - Item: 관리 대상 엔티티 클래스 (데이터베이스의 items 테이블과 매핑됨)
//    - Integer: Item 엔티티의 기본 키(Primary Key) 데이터 타입
//      (Item 클래스의 id 필드가 int 타입이므로 Integer로 설정됨)

// Spring Data JPA의 JpaRepository는 이 두 가지 정보를 사용하여:

// 어떤 엔티티(Item)를 대상으로 데이터베이스 작업을 수행해야 하는지 알아냅니다.
// 기본 키(Integer)를 사용하여 특정 엔티티를 고유하게 식별하고 조회하거나 삭제할 수 있도록 합니다.

// Item 클래스: 데이터베이스의 한 행(row)을 객체로 표현하며, 데이터 구조와 매핑을 관리.
// ItemRepository 클래스: 데이터베이스와 상호작용하여 데이터를 조회, 저장, 수정, 삭제하는 역할.
// 따라서, Item은 데이터 자체, ItemRepository는 데이터에 작업을 수행하는 도구로 이해.