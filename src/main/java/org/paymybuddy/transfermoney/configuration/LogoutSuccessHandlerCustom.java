package org.paymybuddy.transfermoney.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.web.authentication.logout.LogoutHandler;
/*
public class LogoutSuccessHandlerCustom implements LogoutHandler {

    private final UserCache userCache;

    public LogoutSuccessHandlerCustom(UserCache userCache) {
        this.userCache = userCache;
    }
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        String userName = UserUtils.getAuthenticatedUserName();
        userCache.evictUser(userName);
    }
}*/
