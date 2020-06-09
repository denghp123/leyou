package cn.itcast.item.web;

import cn.itcast.item.service.CategoryService;
import cn.itcast.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/5 10:10
 * @Version 1.0
 */

@RestController
@RequestMapping("category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    /**
     * 查询一级分类下的商品分类
     * @param pid
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> selectCategoryByParentId(@RequestParam("pid") Long pid){


        return ResponseEntity.ok(categoryService.selectCategoryByParentId(pid));
    }

    /**
     * 根据品牌id查询商品分类信息
     * @param pid
     * @return
     */
    @GetMapping("/bid/{pid}")
    public ResponseEntity<List<Category>> selectCategoryByPid(@PathVariable("pid")Long pid){
        return ResponseEntity.ok(categoryService.selectCategoryByPid(pid));
    }

}
