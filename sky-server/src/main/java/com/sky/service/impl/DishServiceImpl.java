package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper flavorMapper;
    /**
     * 新增菜品和对应的口味数据
     * @param dishDTO
     */

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //新增菜品
        dishMapper.insert(dish);
        Long id = dish.getId();
        
        //新增口味（n）个
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null &&flavors.size()>0){

            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);

            });
                flavorMapper.insert(flavors);

        }
    }
}
