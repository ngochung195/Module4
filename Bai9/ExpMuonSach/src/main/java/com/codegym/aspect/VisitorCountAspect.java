package com.codegym.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Aspect
@Component
public class VisitorCountAspect {

    private static final Logger logger = Logger.getLogger(VisitorCountAspect.class.getName());
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Biến đếm tổng số lượt ghé thăm và thực hiện thao tác trên thư viện
    private static final AtomicInteger visitorCount = new AtomicInteger(0);

    public static int getVisitorCount() {
        return visitorCount.get();
    }

    // Pointcut bắt mọi thao tác trên Controller của thư viện
    @Pointcut("execution(* com.codegym.controller.BookController.*(..))")
    public void allControllerActions() {}

    // Sử dụng Advice @Before (một Join Point khác với @AfterThrowing theo đúng gợi ý của đề bài)
    @Before("allControllerActions()")
    public void logVisitorInteraction(JoinPoint joinPoint) {
        int count = visitorCount.incrementAndGet();
        String methodName = joinPoint.getSignature().getName();
        String time = LocalDateTime.now().format(dtf);

        String logMessage = String.format(
                ">>> [LOG GHÉ THĂM THƯ VIỆN] Tổng số lượt người ghé thăm/thao tác: %d | Thao tác thực hiện: %s() | Thời gian: %s",
                count, methodName, time
        );

        System.out.println(logMessage);
        logger.info(logMessage);
    }
}
