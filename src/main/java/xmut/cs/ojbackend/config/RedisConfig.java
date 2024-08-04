package xmut.cs.ojbackend.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.Map;

@Configuration
public class RedisConfig {
    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory)
    {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        FastJson2RedisSerializer serializer = new FastJson2RedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();
        return template;
    }

    public static class FastJson2RedisSerializer<T> implements RedisSerializer<T> {

        /**
         * cls.
         */
        private final Class<T> clazz;

        /**
         * 序列化.
         *
         * @param t object to serialize. Can be {@literal null}.
         * @return es
         * @throws SerializationException e
         */
        @Override
        public byte[] serialize(T t) throws SerializationException {
            if (t == null) {
                return new byte[0];
            }
            Map.Entry<String, T> entity = new AbstractMap.SimpleEntry<>(t.getClass().getName(), t);
            return JSON.toJSONString(entity, JSONWriter.Feature.WriteClassName).getBytes(Charset.defaultCharset());
        }

        /**
         * 反序列化.
         *
         * @param bytes object binary representation. Can be {@literal null}.
         * @return es
         * @throws SerializationException e
         */
        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null) {
                return null;
            }
            String str = new String(bytes, Charset.defaultCharset());
            int index = str.indexOf(":");
            String cls = str.substring(2, index - 1);
            String obj = str.substring(index + 1, str.length() - 1);
            return JSON.parseObject(
                    obj,
                    clazz,
                    JSONReader.autoTypeFilter(cls),
                    JSONReader.Feature.SupportClassForName);
        }

        /**
         * 构造器.
         *
         * @param clazz cls
         */
        public FastJson2RedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }
    }
}
