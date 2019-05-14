package com.huang.web.interceptor;

import com.huang.pojo.Business;
import com.huang.pojo.Manager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Component
public class ManagerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        if(url.toLowerCase().indexOf("login")>=0 || url.toLowerCase().indexOf("regist")>=0){
            return true;
        }
        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");
        Business business = (Business) session.getAttribute("business");
        if(manager!=null || business!=null){
            return true;
        }
        response.sendRedirect("/backend/to_login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
