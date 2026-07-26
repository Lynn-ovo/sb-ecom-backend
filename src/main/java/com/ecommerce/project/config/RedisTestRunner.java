package com.ecommerce.project.config;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisTestRunner implements CommandLineRunner {

    private final RedissonClient redissonClient;

    public RedisTestRunner(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void run(String... args) {
        RBucket<String> bucket =
                redissonClient.getBucket("test:connection");

        bucket.set("Redis connected successfully");

        String value = bucket.get();

        System.out.println("Redis test result: " + value);
    }
}