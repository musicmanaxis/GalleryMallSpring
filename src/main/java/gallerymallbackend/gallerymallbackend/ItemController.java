package gallerymallbackend.gallerymallbackend;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

  @GetMapping("/api/items")
  public List<String> getItems(){
    List<String> items=new ArrayList<>();
    items.add("Apple");
    items.add("Banana");
    items.add("Orange");

    return items;
  }
}