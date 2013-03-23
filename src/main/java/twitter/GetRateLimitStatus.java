package twitter;

import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.Map;

/**
 * Gets rate limit status.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class GetRateLimitStatus {
    /**
     * Usage: java twitter4j.examples.account.GetRateLimitStatus
     *
     * @param args message
     */
    public static void main(String[] args) {
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            Map<String, RateLimitStatus> rateLimitStatuses = twitter.getRateLimitStatus();
            for (Map.Entry<String, RateLimitStatus> statusEntry : rateLimitStatuses.entrySet()) {
                RateLimitStatus rateLimitStatus = statusEntry.getValue();
                if (rateLimitStatus.getRemaining() < rateLimitStatus.getLimit()) {
                    System.out.println("key = " + statusEntry.getKey());
                    System.out.println("HourlyLimit: " + rateLimitStatus.getLimit());
                    System.out.println("Remaining: " + rateLimitStatus.getRemaining());
                    System.out.println("ResetTimeInSeconds: " + rateLimitStatus.getResetTimeInSeconds());
                    System.out.println("SecondsUntilReset: " + rateLimitStatus.getSecondsUntilReset());
                }
            }

            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get rate limit status: " + te.getMessage());
            System.exit(-1);
        }
    }
}
