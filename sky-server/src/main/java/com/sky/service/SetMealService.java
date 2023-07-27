package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetMealService {

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuary(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 套餐停售启售
     * @param status
     * @param id
     */
    void stopOnStart(Integer status, Long id);

    /**
     * 套餐批量删除
     * @param ids
     */
    void deleteSetmeal(List<Long> ids);

    /**
     * 根据id查询（数据回显）
     * @param id
     * @return
     */
    SetmealVO listById(Long id);

    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);
    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);

}
