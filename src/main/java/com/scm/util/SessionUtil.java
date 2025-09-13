package com.scm.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionUtil {
    public void removeMessage(){
        try {
            System.out.println("\n RemoveMessage method working\n");
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute("message");
        } catch (Exception e) {
            System.out.println("Error is sessionUtil "+e);
            e.printStackTrace();
        }
    }
}
