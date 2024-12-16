
package gallerymallbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gallerymallbackend.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}

//JpaRepository<Item, Integer>에서 Item은 관리 대상 엔티티 클래스입니다.
//이 Repository는 Item 엔티티와 연결된 데이터베이스 테이블(items)에 대해 CRUD 작업을 수행합니다.

//JpaRepository<Item, Integer>에서 Integer는 Item 엔티티의 기본 키(Primary Key)의 데이터 타입을 나타냅니다.
//Item 클래스에서 기본 키는 id 필드로, 이 필드의 타입이 int입니다


// Spring Data JPA의 JpaRepository는 이 두 가지 정보를 사용하여:

// 어떤 엔티티(Item)를 대상으로 데이터베이스 작업을 수행해야 하는지 알아냅니다.
// 기본 키(Integer)를 사용하여 특정 엔티티를 고유하게 식별하고 조회하거나 삭제할 수 있도록 합니다.

// Item 클래스: 데이터베이스의 한 **행(row)**을 객체로 표현하며, 데이터 구조와 매핑을 관리합니다.
// ItemRepository 클래스: 데이터베이스와 상호작용하여 데이터를 가져오거나 조작하는 작업을 처리합니다.
// 따라서, Item은 데이터 자체, ItemRepository는 데이터에 작업을 수행하는 도구로 이해하면 됩니다.