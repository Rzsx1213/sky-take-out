package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 新增菜品和对应的口味数据
     * @param dishDTO
     */

    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品
     *
     * @param ids
     * @return
     */
     int deleteDish(List<Long> ids);

    /**
     * 根据id查询菜品 （回显数据）
     * @param id
     *
     */
    DishVO getById(Long id);

    /**
     * 修改菜品信息
     * @param dishDTO
     */
    void updateDish(DishDTO dishDTO);
    /**
     * 根据分类id查询菜品
     * @return
     */
    List<Dish> getByCategoryId(Long categoryId);
    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 菜品启售禁售
     * @param dish
     */
    void startOnStop(Dish dish);
}
