package org.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.json.Path;

@Repository
public class RedisDao<T> {

    @Autowired
    private JedisPooled jedisClient;

    public String save(String key, T data) {
        return save(key, "$", data);
    }

    public String save(String key, String path, T data) {
        return jedisClient.jsonSet(key, new Path(path), data);
    }

    public T get(String key, Class<T> clazz) {
        return jedisClient.jsonGet(key, clazz);
    }
}
