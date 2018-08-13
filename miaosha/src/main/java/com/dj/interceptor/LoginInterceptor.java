package com.dj.interceptor;

import com.dj.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录认证拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    //进入Handler方法之前执行
    //用于身份认证，身份授权
    //比如身份认证，如果认证通过表示当前用户没有登录，需要此方法拦截不再向下执行
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取请求的URL
        String url = httpServletRequest.getRequestURI();
        //判断url是否是公开地址（实际使用时将公开地址配置在配置文件中）
        //公开地址
        if(url.indexOf("list")>0 || url.indexOf("login")>0){
            //如果进行登录提交,放心
            return true;
        }
        //判断session
        HttpSession session = httpServletRequest.getSession();
        //从session中取出用户身份信息
        User user = (User) session.getAttribute("user");
        if(user!=null){
            //身份存在，放心
            return true;
        }
        //执行到这里表示用户身份需要认证,跳转到登录相关页面
        //httpServletRequest.getRequestDispatcher("/skill/list").forward(httpServletRequest,httpServletResponse);
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/skill/list");
        return false;
    }
    //进入Handler方法之后，返回modelAndView之前执行
    //应用场景从modelAndView出发：将公用的模型数据(比如菜单导航)在这里传到视图，也可以在这里统一指定视图
    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    //执行Handler完成执行此方法
    //应用场景：统一异常处理，统一日志处理
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
