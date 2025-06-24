package com.example.setting.dto;

import com.example.setting.service.MbtiType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAiRequest {
    private MbtiType mbti;
    private String message;
}