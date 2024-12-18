package gallerymallbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GallerymallbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GallerymallbackendApplication.class, args);
	}

}


//개발순서
// 1.@Entity 클래스 작성(Item Class): 데이터 모델 설계.
// 2.JpaRepository(ItemRepository Class) 작성: 데이터 저장소 계층 정의.
//   (옵션) 서비스 계층 작성: 비즈니스 로직을 처리할 계층 추가.
// 3.컨트롤러 작성(ItemController Class): 사용자 요청을 처리하는 계층 작성.