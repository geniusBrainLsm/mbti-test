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
        headers.set("Authorization", "");  //여기 ""에 APIKEY

        String systemMessage = "당신과의 대화를 통해 각 MBTI 성격유형과의 대화를 체험하려함"; //User role

        //myMbti 바꿀때마다 바꿈(Assistant role)
        String assistantMessage = "나는 " + (String) httpSession.getAttribute("myMbti") + "성격유형을 가졌고 반말만 사용하며 자신의 성격유형에 따른 말투 사용.";

        if (assistantMessage == null || assistantMessage.isEmpty()) {
            assistantMessage = "너는 ESTJ 성격을 강하게 가졌고, 반말밖에 할 수 없어.";  // 디폴트는 ESTJ
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
