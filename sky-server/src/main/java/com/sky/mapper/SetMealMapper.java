package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealMapper {


     @AutoFill(value = OperationType.INSERT)
     void save(Setmeal setmeal);

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 新增套餐菜品
     * @param setmealDishes
     */
    void insertDish(List<SetmealDish> setmealDishes);

    /**
     * 套餐分页
     * @param setmealVO
     * @return
     */
    Page<SetmealVO> pageQuary( SetmealPageQueryDTO setmealVO);

    /**
     * 套餐停售启售
     * @param setmeal
     */
    void updata(Setmeal setmeal);

    /**
     * *删除套餐数据
     * @param id
     */
    void deleteSetmeal(Long id);

    /**
     * 删除套餐对应菜品数据
     * @param id
     */
    void deleteSetmealDish(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SetmealVO listById(Long id);

    /**
     * 根据id查询套餐绑定菜品
     * @param id
     * @return
     */
    List<SetmealDish> listByIdDish(Long id);
    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);
    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

}
