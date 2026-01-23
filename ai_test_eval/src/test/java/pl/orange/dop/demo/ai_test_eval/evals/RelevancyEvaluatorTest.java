package pl.orange.dop.demo.ai_test_eval.evals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RelevancyEvaluatorTest {
    RelevancyEvaluator relevancyEvaluator;

    @BeforeEach
    void setup(@Autowired ChatClient.Builder builder) {
        relevancyEvaluator = new RelevancyEvaluator(builder);
    }

    @Test
    void testRelevantResponse() {
        String userQuery = "What is the capital of Slovakia?";
        List<String> context = List.of(
            "Slovakia is a country in Europe, bordered by Czech Republic, Austria, Ukraine, Poland, and Hungary",
            "The capital city of Slovakia is Bratislava, which also is the largest city.",
            "Slovakian is an official language of Slovakia.");

        String llmResponse = "The capital of Slovakia is Bratislava";
        EvaluationRequest request = new EvaluationRequest(userQuery, contextStringsToDocument(context), llmResponse);
        EvaluationResponse response = relevancyEvaluator.evaluate(request);
        assertTrue(response.isPass(), "The response should be relevant. Feedback: "+response.getFeedback());
    }

    @Test
    void testIrrelevantResponse() {
        String userQuery = "What is the capital of Slovakia?";
        List<String> context = List.of(
            "Slovakia is a country in Europe, bordered by Czech Republic, Austria, Ukraine, Poland, and Hungary",
            "The capital city of Slovakia is Bratislava, which also is the largest city.",
            "Slovakian is an official language of Slovakia.");

        String llmResponse = "I like pizza";
        EvaluationRequest request = new EvaluationRequest(userQuery, contextStringsToDocument(context), llmResponse);
        EvaluationResponse response = relevancyEvaluator.evaluate(request);
        assertFalse(response.isPass(), "The response should be relevant. Feedback: "+response.getFeedback());
    }

    // Document is an object because we sometime want to use RAG
    private List<Document> contextStringsToDocument(List<String> contextStrings) {
        return contextStrings.stream().map(Document::new).toList();
    }
}
