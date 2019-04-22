package com.example.demo.util;

import com.example.demo.vo.UserVo;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ReqUtils {

    private ReqUtils() {
    }

    public static void setUserAttribute(UserVo value) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        requestAttributes.setAttribute("user", value, RequestAttributes.SCOPE_REQUEST);
    }

    public static UserVo removeUserAttribute() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        UserVo t = (UserVo) requestAttributes.getAttribute("user", RequestAttributes.SCOPE_REQUEST);
        requestAttributes.removeAttribute("user", RequestAttributes.SCOPE_REQUEST);
        return t;
    }


}
