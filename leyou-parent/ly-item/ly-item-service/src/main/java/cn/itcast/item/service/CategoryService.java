package cn.itcast.item.service;

import cn.itcast.common.enums.ExceptionEnum;
import cn.itcast.common.exceptions.LyException;
import cn.itcast.item.mapper.CategoryMapper;
import cn.itcast.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author dhp
 * @Date 2020/6/5 10:10
 * @Version 1.0
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    public List<Category> selectCategoryByParentId(Long pid) {

        Category category = new Category();
        category.setParentId(pid);

        List<Category> result = categoryMapper.select(category);

        if (CollectionUtils.isEmpty(result)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }

        return result;
    }
}
