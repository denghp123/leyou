package cn.itcast.item.web;

import cn.itcast.item.service.CategoryService;
import cn.itcast.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
