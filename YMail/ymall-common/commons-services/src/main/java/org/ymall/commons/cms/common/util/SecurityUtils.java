package org.ymall.commons.cms.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**

 **/
public class SecurityUtils {
    private SecurityUtils() {
    }

    public static <T extends UserDetails> T getLoginBody() {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        return (T) auth.getPrincipal();
    }
}
