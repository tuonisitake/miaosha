package com.dj.exception;

/**
 * 秒杀相关业务异常
 */
public class SkillException extends RuntimeException{
    public SkillException(String message) {
        super(message);
    }

    public SkillException(String message, Throwable cause) {
        super(message, cause);
    }
}
