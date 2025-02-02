//이 클래스는 Spring Boot의 컨트롤러로, 클라이언트(예: 브라우저 또는 API 호출자)로부터 요청을 처리하고, 응답 데이터를 반환하는 역할
// -동작 과정 (요청-응답 흐름)
// 1.클라이언트가 GET /api/items 요청을 보냅니다.
// 2.ItemController의 getItems 메서드가 실행됩니다.
// 3.itemRepository.findAll()을 호출하여 데이터베이스에서 items 테이블의 모든 레코드를 가져옵니다.
// 4.조회된 데이터(리스트)가 JSON 형식으로 변환됩니다.
// 5.클라이언트에게 JSON 데이터를 응답으로 보냅니다.
// 6.디비 테이블에 데이터를 꺼내와서 보관하는 것은 List형태로 보관한다.

// **일반적인 작성순서**
// 1.Item 클래스: 데이터의 구조와 단위 객체를 정의합니다.
// 2.ItemRepository 클래스: 데이터베이스와의 상호작용을 처리합니다.
// 3.ItemController 클래스: Item과 ItemRepository를 사용해 데이터를 조회, 조작한 후 클라이언트 요청을 처리하고 결과를 반환합니다.
// 결과적으로, ItemController는 클라이언트와 서버, 데이터베이스 간의 데이터 흐름을 연결하는 중심 역할을 수행합니다.
package gallerymallbackend.controller;
import java.util.List;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import gallerymallbackend.entity.Item;
import gallerymallbackend.repository.ItemRepository;


@RestController //이 클래스가 컨트롤러 역할을 하며, 반환값이 자동으로 JSON 또는 XML형식으로 직렬화되어 HTTP 응답 본문에 포함.
public class ItemController {

@Autowired //스프링이 ItemRepository객체를 자동으로 주입. 별도의 객체 생성 코드 없이 ItemRepository 객체를 바로 사용할 수 있다.   
ItemRepository itemRepository; 

//Vue의 Home컴포넌트에서 요쳥, 하단에 axios.get("/api/items")로 되어 있음
  @GetMapping("/api/items") 
  public List<Item> getItems(){
   List<Item> items=itemRepository.findAll();  //jpa 리포지터리 메서드 반환형이 List<>형태이다.
    return items;  //데이터 조회:    ItemRepository를 통해 데이터베이스에서 items 테이블의 데이터를 조회.
                    // itemRepository.findAll()을 호출하여 데이터베이스의 items 테이블의 모든 레코드를 반환다.
                    //반환된 items는 스프링이 json형태로 변환하여 요청자에게 보여줌
  }
}