package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.exception;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.enums.ResponseEnum;
import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.web.model.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class DynamicThreadPoolWebGlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseVO<?> processBusinessException(BusinessException e) {
        log.warn("动态线程池Web, 出现业务异常", e);
        return ResponseVO.failed(e.getResponseEnum(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseVO<?> processRuntimeException(RuntimeException e) {
        log.error("动态线程池Web, 出现系统异常: {}", e.toString());
        return ResponseVO.failed(ResponseEnum.SYSTEM_ERROR, e.toString());
    }
}
