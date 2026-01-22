package pl.orange.dop.demo.ai_test_eval.evals;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    public final ChatClient chatClient;

    public ReviewService(ChatClient.Builder builder) {
        this.chatClient = builder
            .defaultOptions(OpenAiChatOptions.builder().temperature(0.1d).build())
            .build();
    }

    public Sentiment classifySentiment(String review) {
        String systemPrompt = """
            Classify the sentiment of the following text as POSTIVE, NEGATIVE, or NEUTRAL
            Your response must be only one of those three words.""";

        return chatClient.prompt()
            .system(systemPrompt)
            .user(review)
            .call()
            .entity(Sentiment.class);
    }
}
