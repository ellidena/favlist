package com.favlist.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SessionUtils {
    public static Integer getUserId(HttpSession session) {
        return (Integer) session.getAttribute("userId");
    }

    public static boolean isLoggedIn(HttpSession session) {
        return getUserId(session) != null;
    }

    public static String redirectIfNotLoggedIn(HttpSession session) {
        return (getUserId(session) == null) ? "redirect:/login" : null;
    }

    public static Integer requireLogin(HttpSession session) {
        Integer userId = getUserId(session);
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        }
        return userId;
    }
}
