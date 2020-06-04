package cn.itcast.item.service;

import cn.itcast.pojo.Item;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Author dhp
 * @Date 2020/6/3 14:28
 * @Version 1.0
 */
@Service
public class ItemService {

    public Item saveItem(Item item){
        int i = new Random().nextInt(100);
        item.setId(i);

        return item;
    }
}
