package com.google.couponfinder.config;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author W&F
 * @version 1.0
 * @date 2023/3/29 19:25
 */
@Slf4j
@Configuration
public class QiniuConfig {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Bean
    public Auth getAuth() {
        return Auth.create(accessKey, secretKey);
    }

    @Bean
    public com.qiniu.storage.Configuration getConfiguration() {
        return new com.qiniu.storage.Configuration(Region.region2());
    }

    @Bean
    public UploadManager getUploadManager(com.qiniu.storage.Configuration cfg) {
        cfg.resumableUploadAPIVersion = com.qiniu.storage.Configuration.ResumableUploadAPIVersion.V2;
        return new UploadManager(cfg);
    }

    @Bean
    public BucketManager getBucketManager(Auth auth, com.qiniu.storage.Configuration cfg) {
        return new BucketManager(auth, cfg);
    }
}
