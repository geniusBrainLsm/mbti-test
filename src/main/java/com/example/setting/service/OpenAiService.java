package com.example.setting.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpenAiService {


    private final HttpSession httpSession; //myMbti값 받아오려고 세션주입

    public String sendMessageToOpenai(String userInput) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String systemMessage = "당신과의 대화를 통해 각 MBTI 성격유형과의 대화를 체험하려함"; //User role

        String myMbti = (String) httpSession.getAttribute("myMbti");
        String assistantMessage;

        switch (myMbti) {
            case "istj":
                assistantMessage = "나는 ISTJ 성격 유형을 가졌고, 항상 사실과 질서를 중시해.";
                break;
            case "isfj":
                assistantMessage = "나는 ISFJ 성격 유형을 가졌고, 상대방을 배려하며 말하는 편이야.";
                break;
            case "infj":
                assistantMessage = "나는 INFJ 성격 유형을 가졌고, 이상향을 추구하며 말해.";
                break;
            case "intj":
                assistantMessage = "나는 INTJ 성격 유형을 가졌고, 논리적으로 생각하며 말하곤 해.";
                break;
            case "istp":
                assistantMessage = "나는 ISTP 성격 유형을 가졌고, 실제적이고 논리적으로 말하지.";
                break;
            case "isfp":
                assistantMessage = "나는 ISFP 성격 유형을 가졌고, 감각적이고 실제적인 말투를 사용해.";
                break;
            case "infp":
                assistantMessage = "나는 INFP 성격 유형을 가졌고, 이상과 가치를 중시하며 말하지.";
                break;
            case "intp":
                assistantMessage = "나는 INTP 성격 유형을 가졌고, 이론적이고 논리적인 대화를 선호해.";
                break;
            case "estp":
                assistantMessage = "나는 ESTP 성격 유형을 가졌고, 실질적이고 사실적인 대화를 선호해.";
                break;
            case "esfp":
                assistantMessage = "나는 ESFP 성격 유형을 가졌고, 사람과의 관계를 중시하며 말해.";
                break;
            case "enfp":
                assistantMessage = "나는 ENFP 성격 유형을 가졌고, 가능성을 탐색하며 말하곤 해.";
                break;
            case "entp":
                assistantMessage = "나는 ENTP 성격 유형을 가졌고, 새로운 아이디어를 탐색하며 말하지.";
                break;
            case "estj":
                assistantMessage = "나는 ESTJ 성격 유형을 가졌고, 질서와 규칙을 중시하며 말해.";
                break;
            case "esfj":
                assistantMessage = "나는 ESFJ 성격 유형을 가졌고, 남을 돕고 사회적인 규칙을 중요하게 생각해.";
                break;
            case "enfj":
                assistantMessage = "나는 ENFJ 성격 유형을 가졌고, 남을 이해하려고 노력하며 말해.";
                break;
            case "entj":
                assistantMessage = "나는 ENTJ 성격 유형을 가졌고, 목표를 향해 직선적으로 말하곤 해.";
                break;
            default:
                assistantMessage = "너는 ESTJ 성격을 강하게 가졌고, 반말밖에 할 수 없어.";  // 디폴트는 ESTJ
                break;
        }

        // Create the messages array
        JSONArray messagesArray = new JSONArray();

        JSONObject systemObject = new JSONObject();
        systemObject.put("role", "system");
        systemObject.put("content", systemMessage);
        messagesArray.put(systemObject);

        JSONObject assistantObject = new JSONObject();
        assistantObject.put("role", "assistant");
        assistantObject.put("content", assistantMessage);
        messagesArray.put(assistantObject);

        JSONObject userObject = new JSONObject();
        userObject.put("role", "user");
        userObject.put("content", userInput);
        messagesArray.put(userObject);

        // Create the main request body object
        JSONObject requestBodyObj = new JSONObject();
        requestBodyObj.put("model","gpt-3.5-turbo");
        requestBodyObj.put("messages",messagesArray);

        HttpEntity<String> entity = new HttpEntity<>(requestBodyObj.toString(), headers);

        ResponseEntity<String> responseEntity =
                restTemplate.exchange(
                        "https://api.openai.com/v1/chat/completions",
                        HttpMethod.POST,
                        entity,
                        String.class
                );


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Error from OpenAI: " + responseEntity.getStatusCode());
        }
    }
}
