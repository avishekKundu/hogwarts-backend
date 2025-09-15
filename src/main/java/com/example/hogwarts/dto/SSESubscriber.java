package com.example.hogwarts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Getter
public class SSESubscriber {
    private final SseEmitter emitter;
    private final String windowKey;
    public SSESubscriber(SseEmitter e, String w) {
        this.emitter = e; this.windowKey = w;
    }
}
