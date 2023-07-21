package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    /**
     * 分类分页
     * @param categoryPageQueryDTO
     * @return
     */

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 删除分类
     * @param id
     */

    void delete(Integer id);

    /**
     * 启用禁用菜品
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
    /**
     * 修改分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
