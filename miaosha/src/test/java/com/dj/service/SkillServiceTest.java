package com.dj.service;

import com.dj.domain.Skill;
import com.dj.dto.Exposer;
import com.dj.dto.SkillExcutor;
import com.dj.exception.RepeatKillException;
import com.dj.exception.SkillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class SkillServiceTest {
    @Autowired
    private SkillService skillService;

    @Test
    public void getSkillList() {
        List<Skill> skillList = skillService.getSkillList();
        for (Skill skill : skillList){
            System.out.println(skill);
        }
    }

    @Test
    public void getSkillById() {
        Skill skill = skillService.getSkillById(1);
        System.out.println(skill.getName());
    }

    @Test
    public void exportSkillUrl() {
        Exposer exposer = skillService.exportSkillUrl(1);
        System.out.println(exposer);
        //Exposer{exposed=true, md5='10b4a9d3975a76f795daa3e8dbfffc52', sId=1, now=0, start=0, end=0}
    }
    @Test
    public void skillLogic() {
        Exposer exposer = skillService.exportSkillUrl(2);
        //如果秒杀已开启，输出秒杀信息
        if(exposer.isExposed()){
            System.out.println(exposer);
            //Exposer{exposed=true, md5='10b4a9d3975a76f795daa3e8dbfffc52', sId=1, now=0, start=0, end=0}
            String md5 = exposer.getMd5();
            try {
                SkillExcutor skillExcutor = skillService.executeSkill(2, 66, md5);
                System.out.println(skillExcutor);
                //SkillExcutor{sId=1, state=1, stateInfo='秒杀成功！',
                // successKill=SuccessKill{sID=1, userPhone=66, state=0, createaTime=null,
                // skill=Skill{sId=1, name='1000元秒杀IPhone', number=99, startTime=Fri Jul 20 08:44:50 CST 2018, endTime=Mon Jul 23 08:44:56 CST 2018, createTime=Wed Jul 18 08:45:04 CST 2018}}}
            }catch (RepeatKillException e){
                throw e;
            }catch (SkillCloseException e1){
                throw e1;
            }
        }else {
            System.out.println(exposer);
        }

    }

    @Test
    public void executeSkill() {
        String md5 = "10b4a9d3975a76f795daa3e8dbfffc52";
        try {
            SkillExcutor skillExcutor = skillService.executeSkill(1, 66, md5);
            System.out.println(skillExcutor);
            //SkillExcutor{sId=1, state=1, stateInfo='秒杀成功！',
            // successKill=SuccessKill{sID=1, userPhone=66, state=0, createaTime=null,
            // skill=Skill{sId=1, name='1000元秒杀IPhone', number=99, startTime=Fri Jul 20 08:44:50 CST 2018, endTime=Mon Jul 23 08:44:56 CST 2018, createTime=Wed Jul 18 08:45:04 CST 2018}}}
        }catch (RepeatKillException e){
            throw e;
        }catch (SkillCloseException e1){
            throw e1;
        }

    }
}