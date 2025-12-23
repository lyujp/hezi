package com.lyujp.heziservice.util;

import cn.hutool.crypto.digest.DigestUtil;
import jakarta.servlet.http.HttpServletRequest;

public class IpHelper {

    /***
     * 获取ip地址
     * @param httpServletRequest 请求
     * @return ip
     */
    public static String getIp(HttpServletRequest httpServletRequest){

        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        if(BlankHelper.strIsBlank(ip)){
            ip =  httpServletRequest.getHeader("X-Real-IP");
        }
        if(BlankHelper.strIsBlank(ip)){
            ip = httpServletRequest.getRemoteAddr();
        }

        return DigestUtil.sha512Hex(ip);
    }

    public static String getIpDirect(HttpServletRequest httpServletRequest){
        return DigestUtil.sha512Hex(httpServletRequest.getRemoteAddr());
    }

}
