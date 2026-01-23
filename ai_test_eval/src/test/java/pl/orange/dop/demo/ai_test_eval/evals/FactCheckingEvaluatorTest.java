package pl.orange.dop.demo.ai_test_eval.evals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FactCheckingEvaluatorTest {

    private FactCheckingEvaluator factCheckingEvaluator;

    @BeforeEach
    void setup(@Autowired ChatClient.Builder builder){
        factCheckingEvaluator = FactCheckingEvaluator.builder(builder).build();
    }

    @Test
    void passesWhenClaimIsTrue() {
        String contextDocument = """
            The Eiffel Tower is a wrought iron-lattice tower located on the Champ de Mars in Paris, France. \
            It was designed and build by Gustave Eiffel's company and completed in 1889 as the entrance to the \
            exhibition for the 1889 World's Fair.
            """;

        String aiClaim= "The Eiffel Tower was completed in 1889.";

        // For FactCheckingEvaluator:
        // - userText: can be original query , or claim itself
        // - dataList: The list of context documents against which the claim is checked
        // - responseContent: The AI's claim that needs fact checking

        EvaluationRequest request = new EvaluationRequest(
            "When was Eiffel Tower completed?",
            List.of(new Document(contextDocument)),
            aiClaim
        );

        EvaluationResponse response = factCheckingEvaluator.evaluate(request);

        assertTrue(response.isPass(), "Claim should be factually correct. Feedback: "+response.getFeedback());

    }

    @Test
    void failsWhenClaimIsFalse() {
        String contextDocument = """
            The Eiffel Tower is a wrought iron-lattice tower located on the Champ de Mars in Paris, France. \
            It was designed and build by Gustave Eiffel's company and completed in 1889 as the entrance to the \
            exhibition for the 1889 World's Fair.
            """;

        String aiClaim= "The Eiffel Tower is located in Zurich, Switzerland.";

        // For FactCheckingEvaluator:
        // - userText: can be original query , or claim itself
        // - dataList: The list of context documents against which the claim is checked
        // - responseContent: The AI's claim that needs fact checking

        EvaluationRequest request = new EvaluationRequest(
            "Where is Eiffel Tower located?",
            List.of(new Document(contextDocument)),
            aiClaim
        );

        EvaluationResponse response = factCheckingEvaluator.evaluate(request);

        assertFalse(response.isPass(), "Claim should be factually correct. Feedback: "+response.getFeedback());

    }

}
