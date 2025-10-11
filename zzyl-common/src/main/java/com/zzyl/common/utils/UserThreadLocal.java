package com.zzyl.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * subjectContent.java
 *  用户主体对象
 */
@Slf4j
public class UserThreadLocal {

    private static final ThreadLocal<Long> LOCAL = new ThreadLocal<>();

    private UserThreadLocal() {

    }

    /**
     * 将authUserInfo放到ThreadLocal中
     *
     * @param authUserInfo {@link Long}
     */
    public static void set(Long authUserInfo) {
        LOCAL.set(authUserInfo);
    }

    /**
     * 从ThreadLocal中获取authUserInfo
     */
    public static Long get() {
        return LOCAL.get();
    }

    /**
     * 从当前线程中删除authUserInfo
     */
    public static void remove() {
        LOCAL.remove();
    }

    /**
     * 从当前线程中获取前端用户id
     * @return 用户id
     */
    public static Long getUserId() {
        return LOCAL.get();
    }


}
