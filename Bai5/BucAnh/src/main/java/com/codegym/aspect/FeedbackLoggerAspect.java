package com.codegym.aspect;

import com.codegym.exception.BadWordException;
import com.codegym.model.Feedback;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Aspect
@Component
public class FeedbackLoggerAspect {

    private static final Logger logger = Logger.getLogger(FeedbackLoggerAspect.class.getName());
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @AfterThrowing(
            pointcut = "execution(public * com.codegym.service.IFeedbackService.save(..))",
            throwing = "ex"
    )
    public void logBadFeedback(JoinPoint joinPoint, BadWordException ex) {
        Feedback feedback = ex.getFeedback();
        if (feedback == null && joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof Feedback) {
            feedback = (Feedback) joinPoint.getArgs()[0];
        }

        String author = (feedback != null && feedback.getAuthor() != null) ? feedback.getAuthor() : "Unknown";
        String content = (feedback != null && feedback.getFeedback() != null) ? feedback.getFeedback() : "";
        LocalDateTime time = ex.getTimestamp() != null ? ex.getTimestamp() : LocalDateTime.now();
        String formattedTime = time.format(FORMATTER);

        String logMessage = String.format(
                "%n============================================================%n" +
                "[SPRING AOP - BAD FEEDBACK LOG]%n" +
                "  - Thong bao loi    : %s%n" +
                "  - Tu xau vi pham   : %s%n" +
                "  - Tac gia (Author) : %s%n" +
                "  - Noi dung Feedback: %s%n" +
                "  - Ngay gio Feedback: %s%n" +
                "============================================================",
                ex.getMessage(),
                ex.getBadWord(),
                author,
                content,
                formattedTime
        );

        logger.warning(logMessage);
        System.err.println(logMessage);
    }
}

