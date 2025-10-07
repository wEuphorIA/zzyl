package com.zzyl.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test() {
        System.out.println(redisTemplate);
    }

    @Test
    void testSpring() {
        redisTemplate.opsForValue().set("name","zhangsan");

        String name = redisTemplate.opsForValue().get("name");
        System.out.println(name);

        redisTemplate.opsForValue().set("age","20",20, TimeUnit.SECONDS);

        String age = redisTemplate.opsForValue().get("age");

        System.out.println(age);

        System.out.println(redisTemplate.opsForValue().setIfAbsent("name", "lisi"));

        System.out.println(redisTemplate.opsForValue().setIfAbsent("lock", "lisi"));
    }

    @Test
    void testHash() {

        redisTemplate.opsForHash().put("user", "name", "zhangsan");

        redisTemplate.opsForHash().put("user", "age", "20");

        System.out.println(redisTemplate.opsForHash().get("user", "name"));

        System.out.println(redisTemplate.opsForHash().keys("user"));

        System.out.println(redisTemplate.opsForHash().values("user"));

        redisTemplate.opsForHash().delete("user", "name");
    }

    @Test
    void testList() {

        System.out.println(redisTemplate.opsForList().rightPushAll("mylist","1", "2", "3", "4", "5"));

        System.out.println(redisTemplate.opsForList().range("mylist", 0, -1));

        Long size = redisTemplate.opsForList().size("mylist");

        for (Long i = 0L; i < size; i++) {
            System.out.println(redisTemplate.opsForList().leftPop("mylist") + " ");
        }

    }

    @Test
    void testSet() {

        redisTemplate.opsForSet().add("myset1", "1", "2", "3", "4", "5");

        redisTemplate.opsForSet().add("myset2", "1", "2", "3", "6", "7");

        System.out.println(redisTemplate.opsForSet().members("myset1"));

        // 获取集合大小
        long size = redisTemplate.opsForSet().size("myset1");
        System.out.println(size);

        // 交集
        Set<String> intersection = redisTemplate.opsForSet().intersect("myset1", "myset2");
        System.out.println("交集：" + intersection);

        // 并集
        Set<String> union = redisTemplate.opsForSet().union("myset1", "myset2");
        System.out.println("并集：" + union);
    }


    @Test
    void testZset() {
        redisTemplate.opsForZSet().add("myzset", "zhang", 1);
        redisTemplate.opsForZSet().add("myzset", "zhao", 9);
        redisTemplate.opsForZSet().add("myzset", "qian", 5);

        System.out.println(redisTemplate.opsForZSet().range("myzset", 0, -1));

        Set<ZSetOperations.TypedTuple<String>> myzset = redisTemplate.opsForZSet().rangeWithScores("myzset", 0, -1);

        for (ZSetOperations.TypedTuple<String> typedTuple : myzset) {
            System.out.println(typedTuple.getValue() + " " + typedTuple.getScore());
        }

        redisTemplate.opsForZSet().incrementScore("myzset", "zhang", 10);

        redisTemplate.opsForZSet().remove("myzset", "zhao");
    }

    @Test
    void testCommon() {
        System.out.println(redisTemplate.keys("*"));

        // 判断key是否存在
        Boolean isName = redisTemplate.hasKey("name");
        System.out.println(isName);

        // 获取key的类型
        DataType type = redisTemplate.type("myzset");
        System.out.println(type.name());


    }
}