package utils;

// import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import common.AuthContants;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息类
 */
public class AuthContextHolder {

    /**
     * getUserId 获取当前登录用户id
     *
     * @param request request
     * @return String
     */
    public static String getUserId(HttpServletRequest request) {
        String userId = request.getHeader("userId");
        return StringUtils.isEmpty(userId) ? "" : userId;
    }

    /**
     * getUserTempId 获取当前未登录临时用户id
     *
     * @param request request
     * @return String
     */
    public static String getUserTempId(HttpServletRequest request) {
        String userTempId = request.getHeader(AuthContants.USER_TEMP_ID);
        return StringUtils.isEmpty(userTempId) ? "" : userTempId;
    }
}
