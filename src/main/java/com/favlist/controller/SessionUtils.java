package com.favlist.controller;

import jakarta.servlet.http.HttpSession;

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
}
