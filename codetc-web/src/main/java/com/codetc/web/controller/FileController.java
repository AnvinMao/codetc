package com.codetc.web.controller;

import com.codetc.common.model.api.CommonResult;
import com.codetc.third.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by anvin on 3/8/2021.
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private StorageService storageService;

    @ApiOperation("上传图片")
    @PostMapping(value = "/uploadImage")
    public CommonResult<String> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam(name = "w", required = false) Integer width,
            @RequestParam(name = "h", required = false) Integer height) {
        try {
            String url = this.storageService.uploadImage(
                    image.getInputStream(),
                    image.getOriginalFilename());
            if (url != null) {
                if (width != null || height != null) {
                    url += "?x-oss-process=image/resize,m_fill";
                    if (width != null) {
                        url += ",w_" + width;
                    }
                    if (height != null) {
                        url += ",h_" + height;
                    }
                }

                return CommonResult.success(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return CommonResult.failed("上传失败");
    }
}
