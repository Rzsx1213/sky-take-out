package com.sky.controller.category;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分类分页
     * @param categoryPageQueryDTO
     * @return
     */
    @ApiOperation(value = "分类分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){

        String name = categoryPageQueryDTO.getName();
        log.info("name是{}",name);
        PageResult result = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(result);
    }

    /**
     * 新增菜品分类
     * @param categoryDTO
     * @return
     */

    @PostMapping
    @ApiOperation(value = "新增分类")
    public Result save( @RequestBody  CategoryDTO categoryDTO){

        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public Result deleteByID(Integer id){
            categoryService.delete(id);
        return Result.success();
    }

    /**
     * 启用禁用菜品
     * @param status
     * @param id
     * @return
     */

    @PostMapping("/status/{status}")
    @ApiOperation(value = "启用禁用菜品")
    public Result startOrStop(@PathVariable("status") Integer status, Long id){

        categoryService.startOrStop(status,id);
        return Result.success();
    }
    /**
     * 修改分类
     * @param categoryDTO
     */
    @PutMapping
    @ApiOperation(value = "修改分类")
    public Result  update(@RequestBody CategoryDTO categoryDTO){

        categoryService.update(categoryDTO);


    return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
