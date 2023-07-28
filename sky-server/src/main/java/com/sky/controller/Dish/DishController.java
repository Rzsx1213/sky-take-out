package com.sky.controller.Dish;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){

        dishService.saveWithFlavor(dishDTO);
        //清理缓存数据
        String key ="dish_"+dishDTO.getCategoryId();
        cleanCache(key
        );

        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询")
    public Result<PageResult> dishPage(DishPageQueryDTO dishPageQueryDTO){

        PageResult pageResult =  dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除一个或多个菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "删除菜品")
    public Result deleteDish(@RequestParam List<Long> ids){//利用spring mvc 解析字符串

        cleanCache("dish_*");

        int i = dishService.deleteDish(ids);
        if (i==0){
            return Result.error("删除失败");
        }
        return Result.success();
    }

    /**
     * 根据id查询菜品 （回显数据）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id){

       DishVO dishVO= dishService.getById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品信息
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改菜品信息")
    public  Result updateDish(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据分类id查询菜品")
    public Result< List<Dish> > list(@RequestParam Long categoryId){

        List<Dish> dish =dishService.getByCategoryId(categoryId);


        return  Result.success(dish);
    }

    /**
     * \启售停售
     * @param status
     * @param id
     * @return
     */

    @PostMapping("/status/{status}")
        public Result startOnStop(@PathVariable Integer status ,Long id){
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishService.startOnStop(dish);
        cleanCache("dish_*");

        return Result.success();
    }
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);

    }

}
