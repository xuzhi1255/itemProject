package com.item.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisUtil {

  @Autowired private RedisTemplate<String, String> redisTemplate;

  public Boolean exists(String key) {
    return redisTemplate.hasKey(key);
  }

  public void set(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public long incrby(String key, long value) {
    return redisTemplate.opsForValue().increment(key, value);
  }

  public long decrby(String key, long value) {
    return redisTemplate.opsForValue().decrement(key, value);
  }

  public String get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public boolean hexists(String key, String field) {
    return redisTemplate.opsForHash().hasKey(key, field);
  }

  public boolean hsetnx(String key, String field, String value) {

    return redisTemplate.opsForHash().putIfAbsent(key, field, value);
  }

  public void hset(String key, String field, String value) {
    redisTemplate.opsForHash().put(key, field, value);
  }

  public long hincrby(String key, String field, long value) {
    return redisTemplate.opsForHash().increment(key, field, value);
  }

  public Map<Object, Object> hgetAll(String key) {
    return redisTemplate.opsForHash().entries(key);
  }

  public void zadd(String key, double score, String value) {
    redisTemplate.opsForZSet().add(key, value, score);
  }

  public Set<String> zrevrange(String key, long start, long end) {
    ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
    return zSetOperations.reverseRange(key, start, end);
  }

  public Long zrank(String key, String value) {
    return redisTemplate.opsForZSet().rank(key, value);
  }

  public Double zscore(String key, String oid) {
    return redisTemplate.opsForZSet().score(key, oid);
  }

  public Long zremByScore(String key, Double min, Double max) {
    return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
  }

  public Long zcard(String key) {
    return redisTemplate.opsForZSet().zCard(key);
  }

  public Object hget(String key, String field) {
    return redisTemplate.opsForHash().get(key, field);
  }

  public Long sadd(String key, String value) {
    return redisTemplate.opsForSet().add(key, value);
  }

  public Boolean sismember(String key, String value) {
    return redisTemplate.opsForSet().isMember(key, value);
  }

  public Long srem(String key, String value) {
    return redisTemplate.opsForSet().remove(key, value);
  }

  public Long rpush(String key, String value) {
    return redisTemplate.opsForList().rightPush(key, value);
  }

  public List<String> range(String key, int start, int end) {
    return redisTemplate.opsForList().range(key, start, end);
  }

  public Long lCount(String key) {
    return redisTemplate.opsForList().size(key);
  }

  public void lset(String key, int index, String value) {
    redisTemplate.opsForList().set(key, index, value);
  }

  public Long lrem(String key, long offset, String value) {
    return redisTemplate.opsForList().remove(key, offset, value);
  }
}
