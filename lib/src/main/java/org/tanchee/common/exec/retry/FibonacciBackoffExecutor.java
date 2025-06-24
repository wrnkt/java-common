package org.tanchee.common.exec.retry;

import org.tanchee.common.func.SupplierWithException;

public class FibonacciBackoffExecutor {

    private final long initialDelayMs;
    private final long maxDelayMs;
    private final int maxRetries;

    public FibonacciBackoffExecutor(
        long initialDelayMs,
        long maxDelayMs,
        int maxRetries
    ) {
        this.initialDelayMs = initialDelayMs;
        this.maxDelayMs = maxDelayMs;
        this.maxRetries = maxRetries;
    }

    public <T> T execute(SupplierWithException<T> f) throws Exception {
        long prevDelayMs = 0;
        long currentDelayMs = initialDelayMs;

        int attempt = 1;

        while (true) {
            try {
                T result = f.get();
                return result;
            } catch (Exception e) {
                attempt++;

                if (attempt > maxRetries) {
                    throw new Exception(
                        String.format("Operation failed after %d retries: ", attempt, e)
                    );
                }

                long appliedDelayMs = Math.min(currentDelayMs, maxDelayMs);

                Thread.sleep(appliedDelayMs);

                long nextDelayMs = prevDelayMs + currentDelayMs;
                prevDelayMs = currentDelayMs;
                currentDelayMs = (nextDelayMs > 0) ? nextDelayMs : Long.MAX_VALUE;
            }
        }
    }

}
