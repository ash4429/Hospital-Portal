package com.DoctorAssisstent.PatientPortal2.config;
// import org.springframework.ai.google.genai.GoogleGenAiChatModel;
// import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
// import org.springframework.ai.chat.client.ChatClient;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import com.google.genai.Client;

// @Configuration
public class GeminiConfig {

    // @Bean
    // public ChatClient.Builder chatClientBuilder() {
    //     // 1. Build the low-level Google GenAI SDK Client with your precise API key
    //     Client genAiClient = Client.builder()
    //         .apiKey("AQ.Ab8RN6KUJ9L4Dp1OHxcOFjRjE9MrJti3v5mVDxn2guZquGrbtg")
    //         .build();

    //     // 2. Configure operational model preferences
    //     var chatOptions = GoogleGenAiChatOptions.builder()
    //         .model("gemini-1.5-flash")
    //         .temperature(0.1)
    //         .build();

    //     // 3. Instantiate the ChatModel using the correct fluent builder pattern
    //     var chatModel = GoogleGenAiChatModel.builder()
    //         .genAiClient(genAiClient)
    //         .defaultOptions(chatOptions)
    //         .build();

    //     return ChatClient.builder(chatModel);
    // }
}
