package org.data.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.data.model.params.RedisParams;
import redis.clients.jedis.JedisPooled;

@Configuration
@Log4j2
public class RedisConfig {

    @Autowired
    RedisParams redisParams;

    @Bean
    public JedisPooled jedisClient() {
        JedisPooled pool = new JedisPooled(redisParams.getUrl(), redisParams.getPort());
        log.info("Jedis connected " + pool.getPool().getResource().isConnected() + " at " + redisParams);
        log.info("Redis ping " + pool.getPool().getResource().ping());
        return pool;
    }
}
