package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品数据
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO); //动态sql

    /**
     * 菜品删除
     * @param ids
     * @return
     */
    int delete(Long ids);

    /**
     * 根据id 查菜品信息
     * @param id
     * @return
     */
    Dish getById(Long id);

    /**
     * 修改菜品信息
     * @param dishDTO
     */
    void updateDish(Dish dishDTO);

    /**
     * 根据分类id查询菜品
     * @param categoryId
     */
    @Select("select * from dish where category_id=#{categoryId}")
    List<Dish> findByCategoryId(Long categoryId);

    /**
     * 动态查询菜品数据
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);


}
