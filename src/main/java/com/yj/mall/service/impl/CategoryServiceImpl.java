package com.yj.mall.service.impl;

import com.yj.mall.dao.CategoryMapper;
import com.yj.mall.pojo.Category;
import com.yj.mall.service.ICategoryService;
import com.yj.mall.vo.CategoryVo;
import com.yj.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.yj.mall.consts.MallConst.ROOT_PARENT_ID;

/**
 * create by 86136
 * 2022/3/27 16:55
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<Category> categories = categoryMapper.selectAll();

//        List<CategoryVo> categoryVoList = new ArrayList<>();
        //查出parent_id=0的数据
//        for (Category category : categories) {
//            if (category.getParentId().equals(ROOT_PARENT_ID)){
//                CategoryVo categoryVo = new CategoryVo();
//                BeanUtils.copyProperties(category,categoryVo);
//                categoryVoList.add(categoryVo);
//            }
//        }
        //lambda+stream的写法
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                .map(this::categoryToCategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
        //查询子目录
        findSubCategory(categoryVoList, categories);

        return ResponseVo.success(categoryVoList);
    }

    @Override
    public void findSubCategoryById(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryById(id,resultSet,categories);
    }

    public void findSubCategoryById(Integer id, Set<Integer> resultSet, List<Category> categories) {
        for (Category category : categories) {
            if (category.getParentId().equals(id)) {
                resultSet.add(category.getId());
                findSubCategoryById(category.getId(), resultSet,categories);
            }
        }
    }

    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryList = new ArrayList<>();
            for (Category category : categories) {
                //如果查到内容还要继续查
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = categoryToCategoryVo(category);
                    subCategoryList.add(subCategoryVo);
                }
            }
            findSubCategory(subCategoryList, categories);
            //根据优先级排序
            subCategoryList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
            categoryVo.setSubCategory(subCategoryList);
        }
    }

    private CategoryVo categoryToCategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
