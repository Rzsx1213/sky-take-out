package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 新增口味
     * @param flavors
     */
    void insert(  List<DishFlavor> flavors);

    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getByDishId(Long id);

    void updateFlavor(DishFlavor dishFlavor);

    /**
     * 删除口味
     * @param id
     */
    void remove(Long id);
}
