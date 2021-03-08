package com.codetc.third.service;

import java.io.InputStream;

/**
 * 存储 service
 * Created by anvin on 1/16/2021.
 */
public interface StorageService {

    String uploadImage(InputStream inputStream, String sourceName);

    void deleteFile(String key);
}
