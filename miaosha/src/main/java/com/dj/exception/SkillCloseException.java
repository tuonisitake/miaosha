package com.dj.exception;

/**
 * 秒杀关闭异常
 */
public class SkillCloseException extends SkillException{
    public SkillCloseException(String message) {
        super(message);
    }

    public SkillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
