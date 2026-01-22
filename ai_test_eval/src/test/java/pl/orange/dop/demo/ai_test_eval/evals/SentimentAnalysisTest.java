package pl.orange.dop.demo.ai_test_eval.evals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SentimentAnalysisTest {

    @Autowired
    ReviewService reviewService;

    @Test
    void testPositiveSentiment() {
        String positiveReview = "I love this product, it works great and exceeds my expectations.";
        Sentiment sentiment = reviewService.classifySentiment(positiveReview);
        assertEquals(Sentiment.POSITIVE, sentiment, "The sentiment should be classified as POSITIVE.");
    }

    @Test
    void testNegativeSentiment() {
        String negativeReview = "This is the worst experience I've ever had. The product is terrible.";
        Sentiment sentiment = reviewService.classifySentiment(negativeReview);
        assertEquals(Sentiment.NEGATIVE, sentiment, "The sentiment should be classified as NEGATIVE.");
    }

    @Test
    void testNeutralSentiment() {
        String neutralReview = "It's okay, does what it should do.";
        Sentiment sentiment = reviewService.classifySentiment(neutralReview);
        assertEquals(Sentiment.NEUTRAL, sentiment, "The sentiment should be classified as NEUTRAL.");
    }

}
