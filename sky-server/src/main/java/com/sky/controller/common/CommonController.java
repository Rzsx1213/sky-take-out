package com.sky.controller.common;


import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;


    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public Result<String>  upload(MultipartFile file){
        log.info("文件上传{}",file);

        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //利用UUID来创建文件名
           String objectName= UUID.randomUUID().toString()+extension;

            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
           return Result.success(filePath);
        } catch (IOException e) {
           log.error("文件上传失败{}",e);
            System.out.println("56666666");
        return Result.error(MessageConstant.UPLOAD_FAILED);
        }

    }


}
