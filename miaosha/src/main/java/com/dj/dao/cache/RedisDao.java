package com.dj.dao.cache;

import com.dj.domain.Skill;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis数据访问对象,玩redis里传递Skill对象,获取Skill对象
 */
public class RedisDao {

    private final JedisPool jedisPool;

    //构造方法
    public RedisDao(String ip,int port){
        //初始化
        jedisPool = new JedisPool(ip,port);
    }
    //通过字节码看哪些字节码有什么属性，把字节码传递给那些属性，帮序列化对象
    private RuntimeSchema<Skill> schema = RuntimeSchema.createFrom(Skill.class);

    public Skill getSkill(Integer sId){
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "skill:"+sId;
                //并没有实现内部序列化操作
                //存储的都是二进制数组 get->byte[] -> 反序列化 ->Object(Skill)
                //采用自定义序列化
                //protostuff : pojo
                byte[] bytes = jedis.get(key.getBytes());
                //从缓存中获取到了,用protostuff转化
                if (bytes != null) {
                    //空对象
                    Skill skill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, skill, schema);
                    //Skill被反序列化
                    return skill;
                }
            }finally {
                jedis.close();
            }

        }catch (Exception e){
            throw e;
        }
        return null;
    }

    public String putSkill(Skill skill){
        //set Object(Skill) -> 序列化-> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "skill:"+skill.getsId();
                //缓存器LinkedBuffer，方便当前对象特别大的时候，会有缓冲过程
                byte[] bytes = ProtostuffIOUtil.toByteArray(skill,schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60*60;//一小时
                //如果是错误。返回错误信息，如果正确，返回OK
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            throw e;
        }
    }
}
