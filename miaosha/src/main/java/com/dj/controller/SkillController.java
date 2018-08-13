package com.dj.controller;

import com.dj.domain.Skill;
import com.dj.domain.SuccessKill;
import com.dj.domain.User;
import com.dj.dto.Exposer;
import com.dj.dto.SkillExcutor;
import com.dj.dto.SkillResult;
import com.dj.enums.SkillStatEnum;
import com.dj.exception.RepeatKillException;
import com.dj.exception.SkillCloseException;
import com.dj.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/skill")//url：/模块/资源/{id}/细分
public class SkillController {

    @Autowired
    private SkillService skillService;
    /**
     * 获取秒杀商品的列表页
     * @return
     */
    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(Model model){
        //获取列表页
        List<Skill> skillList = skillService.getSkillList();
        model.addAttribute("list",skillList);
        return "list";
    }

    /**
     * 获取详情页
     */
    @RequestMapping(value = "/{sId}/detail",
            method = RequestMethod.GET
    )
    public String detail(@PathVariable("sId")Integer sId,Model model){
        if(sId == null){
            return "redirect:/skill/list";
        }
        Skill skill = skillService.getSkillById(sId);
        if(skill == null){
            return "forward:/skill/list";
        }
        model.addAttribute("skill",skill);
        return "detail";
    }
    /**
     * 根据用户名查询SuccessKill并携带秒杀商品
     */
    @RequestMapping(value = "/myPrize",method = RequestMethod.POST)
    @ResponseBody
    public SkillResult<SuccessKill> findMySuccesskill(HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            return new SkillResult<SuccessKill>(false,"当前用户不存在！");
        }
        String userName = user.getUsername();
        SkillResult<SuccessKill> result;
        try {
            List<SuccessKill> killList = skillService.queryByUserNameWithSkill(userName);
            result = new SkillResult<SuccessKill>(true,killList);
        } catch (Exception e) {
            result = new SkillResult<SuccessKill>(false,"内部错误！");
        }
        return result;
    }

    /**
     *  暴露秒杀的接口地址
     */
    @RequestMapping(value = "/{sId}/exposer",method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SkillResult<Exposer> exposer(@PathVariable("sId") Integer sId){
        SkillResult<Exposer> result;
        try {
            Exposer exposer = skillService.exportSkillUrl(sId);
            result = new SkillResult<Exposer>(true,exposer);
        }catch (Exception e){
            result = new SkillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }
    /**
     * 执行秒杀
     *  @CookieValue从cookie里获取phone
     *  required = false cookie不是必须的，在方法内执行逻辑，否则报错
     */
    @RequestMapping(value = "/{sId}/{md5}/executor",method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SkillResult<SkillExcutor> excute(@PathVariable("sId")Integer sId,
                                            @PathVariable("md5")String md5,
                                            @CookieValue(value = "killPhone",required = false) Integer phone,
                                            HttpSession httpSession){
        if(phone == null){
            return new SkillResult<SkillExcutor>(false,"未注册！");
        }
        User user = (User) httpSession.getAttribute("user");
        if(user == null){
            return new SkillResult<SkillExcutor>(false,"用户不存在！");
        }
        String userName = user.getUsername();
        SkillResult<SkillExcutor> result;
        try {
            SkillExcutor excutor = skillService.executeSkill(sId,phone,userName,md5);
            result = new SkillResult<SkillExcutor>(true,excutor);
        }catch (SkillCloseException e1){
            SkillExcutor excutor = new SkillExcutor(sId,SkillStatEnum.END);
            result = new SkillResult<SkillExcutor>(true,excutor);
        } catch (RepeatKillException e2) {
            SkillExcutor excutor = new SkillExcutor(sId,SkillStatEnum.REPEAT_KILL);
            result = new SkillResult<SkillExcutor>(true,excutor);
        }catch (Exception e){
            SkillExcutor excutor = new SkillExcutor(sId,SkillStatEnum.INNER_ERROR);
            result = new SkillResult<SkillExcutor>(true,excutor);
        }
        return result;
    }
    /**
     * 获取系统时间
     */
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SkillResult<Long> time(){
        Date date = new Date();
        return new SkillResult<Long>(true,date.getTime());
    }
}
