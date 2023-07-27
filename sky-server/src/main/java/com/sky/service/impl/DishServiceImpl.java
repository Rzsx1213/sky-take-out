package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //开始分页
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        //查询数据
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);


        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public int deleteDish(List<Long> ids) {


        if (ids.size()==1){
            return   dishMapper.delete(ids.get(0));
        }

        for (int i = 0; i <ids.size(); i++) {

            dishMapper.delete(ids.get(i));
        }
        return 1;
    }

    /**
     * 根据id查询菜品数据
     * @param id
     */
    @Override
    public DishVO getById(Long id) {

        //根据id信息查询出菜品信息先把除口味信息放进去
       Dish dish= dishMapper.getById(id);//

        //然后再去查找口味信息

        List<DishFlavor> dishFlavor =  flavorMapper.getByDishId(id);

        //将两条信息合并到DishVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavor);

        return dishVO;

    }

    /**
     * 修改菜品信息
     * @param dishDTO
     */
    @Override
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //修改菜品基本信息
        dishMapper.updateDish(dish);
        //删除口味信息
        flavorMapper.remove(dish.getId());
        //新增口味信息
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null&&flavors.size()>0){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });
            flavorMapper.insert(flavors);

        }



    }
    /**
     * 根据分类id查询菜品
     * @return
     */
    @Override
    public List<Dish> getByCategoryId(Long categoryId) {

        List<Dish> dishList = dishMapper.findByCategoryId(categoryId);


        return dishList;
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {

        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = flavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

    /**
     * 菜品启售仅售
     * @param dish
     */
    @Override
    public void startOnStop(Dish dish) {

        dishMapper.updateDish(dish);

    }
}
