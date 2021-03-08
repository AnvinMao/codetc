package com.codetc.third.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.codetc.third.config.OssProperties;
import com.codetc.third.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * Oss 存储
 * Created by anvin on 1/16/2021.
 */
@Slf4j
@Service
public class OssStorageServiceImpl implements StorageService {

    @Resource
    private OssProperties properties;

    private OSS getOssClient() {
        return new OSSClientBuilder().build(
                this.properties.getEndpoint(),
                this.properties.getAccessKeyId(),
                this.properties.getAccessKeySecret());
    }

    /**
     * 上传文件
     * @param inputStream 文件流
     * @param sourceName 原文件名
     * @return 预览地址
     */
    @Override
    public String uploadImage(InputStream inputStream, String sourceName) {
        String suffix = sourceName.substring(sourceName.lastIndexOf(".")).toLowerCase();
        for (String type: this.properties.getAcceptType()) {
            if (suffix.equals(type)) {
                return null;
            }
        }

        OSS ossClient = this.getOssClient();
        try {
            if (!ossClient.doesBucketExist(this.properties.getBucketName())) {
                ossClient.createBucket(this.properties.getBucketName());
                ossClient.setBucketAcl(this.properties.getBucketName(), CannedAccessControlList.PublicRead);
            }

            String uuid = UUID.randomUUID().toString();
            String objectName = uuid + suffix;
            DateTime dateTime = new DateTime();
            String path = dateTime.toString("yyyy") + "/" + dateTime.toString("MM");
            if (this.properties.getDir() != null) {
                objectName = this.properties.getDir() + "/" + path + "/" + objectName;
            } else {
                objectName = path + "/" + objectName;
            }

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(this.getContentType(suffix));
            ossClient.putObject(this.properties.getBucketName(), objectName, inputStream);
            return this.previewUrl(objectName);
        } catch (OSSException oe) {
            log.error("Caught an OSSException.", oe.getMessage());
        } catch (ClientException ce) {
            log.error("Caught an ClientException.", ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return null;
    }

    private String previewUrl(String objectName) {
        if (this.properties.getPreviewUrl() == null) {
            return "//" + this.properties.getBucketName() + "." + this.properties.getEndpoint() + "/" + objectName;
        } else {
            if (this.properties.getPreviewUrl().endsWith("/")) {
                return this.properties.getPreviewUrl() + objectName;
            } else {
                return this.properties.getPreviewUrl() + "/" + objectName;
            }
        }
    }

    /**
     * 删除文件
     * @param key key
     */
    @Override
    public void deleteFile(String key) {
        OSS ossClient = this.getOssClient();
        try {
            ossClient.deleteObject(this.properties.getBucketName(), key);
        } finally {
            ossClient.shutdown();
        }
    }

    private String getContentType(String suffix) {
        suffix = suffix.toLowerCase();
        switch (suffix) {
            case ".jpg":
            case ".jpeg":
            case ".png":
                return "image/jpg";
            case ".bmp":
                return "image/bmp";
            case ".gif":
                return "image/gif";
        }

        return "image/jpg";
    }
}
