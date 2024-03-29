package be.kdg.processor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import javax.annotation.PostConstruct;

/**
 * Retry config.
 * Used for the retrying the calls on the proxy's
 */
@Configuration
public class RetryConfig {
    public static RetryTemplate retryTemplate;
    private static final long DEFAULT_DELAY = 2500L;
    private static final int DEFAULT_ATTEMPTS = 2;

    /**
     * contrucrs the template with default values
     *
     * @return configured template
     */
    @PostConstruct
    public static RetryTemplate configureTemplate() {
        retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(DEFAULT_DELAY);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(DEFAULT_ATTEMPTS);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    /**
     * @param attempts new number of attempts
     */
    public static void setMaxAttemps(int attempts) {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();

        if (attempts <= 0) retryPolicy.setMaxAttempts(DEFAULT_ATTEMPTS);
        else retryPolicy.setMaxAttempts(attempts);

        retryTemplate.setRetryPolicy(retryPolicy);
    }

    /**
     * @param delay new delay
     */
    public static void setDelay(long delay) {
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();

        if (delay <= 0) fixedBackOffPolicy.setBackOffPeriod(DEFAULT_DELAY);
        else fixedBackOffPolicy.setBackOffPeriod(delay);

        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
    }
}
