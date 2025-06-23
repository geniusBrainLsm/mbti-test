package com.example.setting.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MbtiType {
    ISTJ, ISFJ, INFJ, INTJ,
    ISTP, ISFP, INFP, INTP,
    ESTP, ESFP, ENFP, ENTP,
    ESTJ, ESFJ, ENFJ, ENTJ;
}