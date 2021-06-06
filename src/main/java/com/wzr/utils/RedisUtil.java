//package com.wzr.utils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//
//@Component
//public class RedisUtil
//{
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    /**
//     * zet增加操作
//     * @param key    文章id
//     * @param value  浏览量
//     * @param c    具体数值
//     * @return
//     */
//    public Boolean set(String key, String value, Integer c){
//        try {
////            redisTemplate.opsForZSet().add("viewNum", "h1", Double.valueOf(h1.get("viewNum").toString()));
//
//            redisTemplate.opsForZSet().add(key, value, c);
//
//            return true;
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    public Boolean incr(String key, String value, Integer delta){
//        try {
//            redisTemplate.opsForZSet().incrementScore(key, value, delta);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    public Object get(String key, Object o)
//    {
//        return key == null ? null : redisTemplate.opsForZSet().score(key, o);
//    }
//}
