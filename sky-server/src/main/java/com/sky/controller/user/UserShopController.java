package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@Api(tags = "店铺相关接口")
@RestController
@RequestMapping("/user/shop")
@Slf4j
public class UserShopController {
    private static final String KEY ="SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询店铺营业状态
     * @param
     * @return
     */
    @ApiOperation(value = "获取店铺的营业状态")
    @GetMapping("/status")
    public  Result<Integer> getStatus(){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer shop_status = (Integer) valueOperations.get(KEY);

        return Result.success(shop_status);
    }


}
