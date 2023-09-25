package com.example.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDto {
    private String content;

    public ReplyDto() {
    }

    public ReplyDto(String content) {
        this.content = content;
    }

    //
}
