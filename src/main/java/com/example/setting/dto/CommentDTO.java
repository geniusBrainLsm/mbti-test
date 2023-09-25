package com.example.setting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private String content;

    public CommentDTO() {
    }

    public CommentDTO(String content) {
        this.content = content;
    }

    //
}
