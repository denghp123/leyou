package cn.itcast.item.web;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import cn.itcast.item.service.ItemService;
import cn.itcast.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dhp
 * @Date 2020/6/2 23:49
 * @Version 1.0
 */
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("hello")
    public String hello(){
        return "hello 乐游！";
    }

    @PostMapping("hehe")
    public ResponseEntity<Item> saveItem(Item item){
        if (item.getPrice()== null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

//            throw new RuntimeException("价格不能为空！");

//            throw new LyException(400,"价格不能为空！");
            throw new LyException(ExceptionEnum.PRICE_CANNOT_BE_NULL);

        }

        Item result = itemService.saveItem(item);
//        return ResponseEntity.status(HttpStatus.CREATED).body(result);
        return ResponseEntity.ok(item);
    }
}
