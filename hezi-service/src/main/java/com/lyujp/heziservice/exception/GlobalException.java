package com.lyujp.heziservice.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import com.lyujp.heziservice.dto.ResDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NotLoginException.class)
    public ResDto<Void> handlerNotLoginException(NotLoginException nle) throws Exception {
        // 判断场景值，定制化异常信息
        String message = "";
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未能读取到有效 token";
        }
        else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token 无效";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token 已过期";
        }
        else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token 已被顶下线";
        }
        else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token 已被踢下线";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_FREEZE)) {
            message = "token 已被冻结";
        }
        else if(nle.getType().equals(NotLoginException.NO_PREFIX)) {
            message = "未按照指定前缀提交 token";
        }
        else {
            message = "当前会话未登录";
        }

        // 返回给前端
        return ResDto.fail(message);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResDto<?> handle404Exception(NoHandlerFoundException e) {
        String msg = "接口不存在：" + e.getRequestURL();
        log.error(msg, e);
        return ResDto.fail(HttpStatus.NOT_FOUND.value(), msg);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResDto<Void> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常：", e); // 打印完整堆栈（便于排查）
        return ResDto.fail("系统运行异常:" + HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(Exception.class)
    public ResDto<Void> handleAllException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("全局异常拦截：请求路径[{}]，异常信息：", requestUri, e);
        return ResDto.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误，请联系管理员");
    }

}
