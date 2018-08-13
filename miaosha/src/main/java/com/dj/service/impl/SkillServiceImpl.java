package com.dj.service.impl;

import com.dj.dao.SkillDao;
import com.dj.dao.SuccessKillDao;
import com.dj.dao.cache.RedisDao;
import com.dj.domain.Skill;
import com.dj.domain.SuccessKill;
import com.dj.dto.Exposer;
import com.dj.dto.SkillExcutor;
import com.dj.enums.SkillStatEnum;
import com.dj.exception.RepeatKillException;
import com.dj.exception.SkillCloseException;
import com.dj.exception.SkillException;
import com.dj.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillDao skillDao;
    @Autowired
    private SuccessKillDao successKillDao;
    @Autowired
    private RedisDao redisDao;

    private final String slat = "adadadaf";//md5盐值字符串,用于混淆md5

    public List<Skill> getSkillList() {
        return skillDao.queryAll(0,4);
    }

    public Skill getSkillById(Integer sId) {
        return skillDao.queryById(sId);
    }

    public Exposer exportSkillUrl(Integer sId) {
        //优化点：缓存优化:超时的基础上维护一致性
        /**
         * get from cache
         * if null
         *   get db
         *   else
         *     put cache
         *  locgoin
         */
        //1.访问redis
        Skill skill = redisDao.getSkill(sId);
        if(skill == null){
            //2.如果缓存没有就访问数据库
            skill = skillDao.queryById(sId);
            //如果数据库也没有
            if(skill == null){
                return new Exposer(false,sId);
            }else {
                //3.放入redis
                redisDao.putSkill(skill);
            }
        }
        Date startTime = skill.getStartTime();
        Date endTime = skill.getEndTime();
        Date now = new Date();//当前系统时间\
        if(now.getTime()<startTime.getTime() || now.getTime() > endTime.getTime()){
            return new Exposer(false,sId,now.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5 = getMd5(sId);

        return new Exposer(true,md5,sId);
    }
    private String getMd5(Integer sId){
        String base = sId+"/"+slat;
        //Spring提供的工具类
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /**
     * 使用注解控制事务的优点：
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作（缓存，Redis，HTTP请求）或者剥离到事务方法外部
     * 3.不是所有方法都需要事务，如只有一条修改操作,只读操作不需要事务控制
     */
    public SkillExcutor executeSkill(Integer sId, Integer userPhone,String userName, String md5)
            throws SkillException, SkillCloseException, RepeatKillException {
        if(md5 == null || !md5.equals(getMd5(sId))){
            throw new SkillException("Skill data rewrite");
        }
        //执行秒杀逻辑：减库存，增加购买记录
        //优化：先记录购买行为，再减库存
        Date now = new Date();
        try {
            //记录购买行为
            int insertCount = successKillDao.insertSuccessKill(sId, userPhone,userName);
            //唯一：sId和userPhone
            if(insertCount <= 0){
                throw new RepeatKillException("重复秒杀！");
            }else {
                //减库存,热点商品的竞争
                int i = skillDao.reduceNumber(sId, now);
                if(i <=0 ){
                    //没有更新到记录，秒杀结束，rollback
                    throw new SkillCloseException("skill is closed");
                } else {
                    //秒杀成功，commit
                    SuccessKill successKill = successKillDao.queryByIdWithSkill(sId, userName);
                    return new SkillExcutor(sId, SkillStatEnum.SUCCESS, successKill);
                }
            }

        } catch (SkillCloseException e1){
            throw e1;
        } catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            //所有编译器异常转化为运行期异常
            throw new SkillException("内部错误:"+e.getMessage());
        }
    }

    /**
     * 根据手机号和用户名查询SuccessKill并携带秒杀商品
     */
    public List<SuccessKill> queryByUserNameWithSkill(String userName) throws SkillException {
        List<SuccessKill> killList = successKillDao.queryByUserNameWithSkill(userName);
        if(killList!=null && killList.size()>0){
            return killList;
        }
        return null;
    }
}
