// *Dto클래스의 역할:
// 클라이언트가 보낸 데이터를 서버에서 처리하기 위해 전달받거나, 서버가 클라이언트로 데이터를 전달하기 위한 용도로 사용
// 클라이언트로부터 넘어온 OrderDto는 Order 엔티티로 변환된 후 데이터베이스에 저장 
// 이 과정은 컨트롤러에서 이루어지며, DTO와 엔티티를 분리하여 코드의 가독성과 보안성을 높입니다.
//사용자가 입력한 정보(제이슨 형태로 전송)->컨트롤러에서 제이슨을 dto를 받아 엔티티로 값을 넘김->레포지터리로 저장하면 디비에 정보가 저장
//*전체 흐름: JSON -> DTO -> Entity -> Repository -> Database

package gallerymallbackend.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDto {

    private String name;
    private String address;
    private String payment;
    private String cardNumber;
    private String items;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime orderDate;
    
}
