package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();

        BeanUtils.copyProperties(setmealDTO,setmeal);
        //新增套餐基本信息
        setMealMapper.save(setmeal);
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (int i = 0; i < setmealDishes.size(); i++) {
            setmealDishes.get(i).setSetmealId(id);

        }
        //新增套餐菜品信息
        setMealMapper.insertDish(setmealDishes);


    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuary(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page= setMealMapper.pageQuary(setmealPageQueryDTO);


        long total = page.getTotal();
        return new PageResult(total,page.getResult());
    }

    /**
     * 套餐套餐停售启售
     * @param status
     * @param id
     */
    @Override
    public void stopOnStart(Integer status, Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setMealMapper.updata(setmeal);


    }

    /**
     * 套餐批量删除
     * @param ids
     */
    @Override
    @Transactional
    public void deleteSetmeal(List<Long> ids) {

        for (int i = 0; i < ids.size(); i++) {
            //删除套餐数据
            setMealMapper.deleteSetmeal(ids.get(i));

            //删除套餐对应的菜品表
            setMealMapper.deleteSetmealDish(ids.get(i));

        }


    }

    /**
     *根据id进行查询
     * @param id
     * @return
     */
    @Override
    @Transactional
    public SetmealVO listById(Long id) {
        //创建一个绑定套餐的菜品对象
        SetmealDish setmealDish = new SetmealDish();
        //利用id对菜品进行查询
        List<SetmealDish> dish =setMealMapper.listByIdDish(id);
        //对菜品基本信息以及分类一起查询
        SetmealVO setmealVO= setMealMapper.listById(id);
        //将dish对象放到setmealVO里
        setmealVO.setSetmealDishes(dish);
        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setUpdateUser(BaseContext.getCurrentId());
        //修改套餐基本信息
        setMealMapper.updata(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //删除原有的套餐绑定菜品
        setMealMapper.deleteSetmealDish(setmealDTO.getId());
        //新增对应套餐菜品
            setMealMapper.insertDish(setmealDishes);

    }


}
