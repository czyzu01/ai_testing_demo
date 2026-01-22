# Spring AI evaluation framework

## Core interface

```java
import org.springframework.ai.evaluation.EvaluationRequest;

@FunctionalInterface
public interface Evaluator {
    EvaluatorResponse evaluate(EvaluationRequest evaluationRequest);
}
```

## Evaluation Requests Components
* userText - Orignal text submitted by the user
* dataList - Context data (e.g. from RAG)
* responseContent - AI's model response


## Key evaluators
### Relevancy Evaluator
* **Purpose** Is there AI response relevant to the user's question?
* Best for:
  * RAG
  * Ensuring responses stay on topic
  * Quality controls for chatbots
* What it does:
  * Compares AI response against retrieved context
  * Asks: Does this response answers the user's questions?
  * Returns **Yes**/No evaluation
* Default prompt template:
```
Your task is to evaluate if the response for the query is in line with the context information provided.
```

### Fact checking evaluator
* **Purpose** Is the AI response is factually accurate?
* Best for:
    * Hallucination detection
    * Verifying claims against source material
    * Content validation
* What it does:
    * Verifies claims against provided documents
    * Detects factual inaccuracies and hallucinations
    * Can use specialized models like `Bespoke-Minichecks` (Accurate, small, fast, Cost-effective)
* Evaluation Format
```
  Document: {context}
  Claim: {ai_resposne}
```
## Testing deterministic AI tasks
### Classification tasks 
```
More predictable outcomes = Traditional testing approaches
```
Examples:
* Sentiment Analysis (positive/negative/neutral)
* Content Moderation (safe/unsafe)
* Intent detection (question/request/complaint)

### Testing deterministic AI tasks
```java
@Test
void testSentimentClassification() {
    String positiveText = "I love this product";
    String result = classifySentiment(positiveText);
    
    assertThat(result).isEqualTo("Positive");
}
```
**Key difference: Expected outcomes are _known_ and _consistent_.**
