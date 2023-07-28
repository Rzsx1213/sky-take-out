package com.sky.controller.setmeal;


import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
@RestController
public class SetMealController {
    @Autowired
    private SetMealService setMealService;


    /**
     * 新增套餐
     *
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增套餐")
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result  insert(@RequestBody SetmealDTO setmealDTO){

        setMealService.save(setmealDTO);

    return Result.success();
    }

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询")
    public Result<PageResult> pageSetmeal( SetmealPageQueryDTO setmealPageQueryDTO){

         PageResult page = setMealService.pageQuary(setmealPageQueryDTO);

        return Result.success(page);
    }

    /**
     * 套餐停售启售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "套餐停售启售")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result stopOnStart(@PathVariable Integer status,Long id){
        setMealService.stopOnStart(status,id);

        return Result.success();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "批量删除套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result deleteSetmeal(@RequestParam  List<Long> ids){

        setMealService.deleteSetmeal(ids);

        return Result.success();
    }

    /**
     * 根据id查询（数据回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询")

    public  Result<SetmealVO> listById(@PathVariable Long id){

       SetmealVO setmealVO= setMealService.listById(id);

        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改套餐")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody  SetmealDTO setmealDTO){

            setMealService.updateSetmeal(setmealDTO);

        return Result.success();
    }

}
