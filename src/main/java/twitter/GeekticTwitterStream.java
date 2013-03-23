package twitter;

import services.GeekCommander;
import twitter4j.*;

import javax.inject.Inject;

/**
 * User: xavierhanin
 * Date: 3/23/13
 * Time: 1:22 PM
 */
public class GeekticTwitterStream {

    private final GeekCommander commander;

    @Inject
    public GeekticTwitterStream(GeekCommander commander) {
        this.commander = commander;
    }

    public void start() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                GeekticTwitterStream.this.onStatus(new TwitterStatus()
                        .setScreenName(status.getUser().getScreenName())
                        .setName(status.getUser().getName())
                        .setStatus(status.getText()));
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            public void onScrubGeo(long l, long l2) {
            }

            public void onStallWarning(StallWarning stallWarning) {
            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        twitterStream.filter(new FilterQuery().track(new String[]{"#geektic"}));
    }

    public void onStatus(TwitterStatus twitterStatus) {
        GeekCommander.GeekCommand command = commander.parse(twitterStatus.getName(), twitterStatus.getStatus());
        command.run();
    }

    public static class TwitterStatus {
        private String screenName;
        private String name;
        private String status;

        @Override
        public String toString() {
            return "TwitterStatus{" +
                    "screenName='" + screenName + '\'' +
                    ", name='" + name + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }

        public TwitterStatus setScreenName(final String screenName) {
            this.screenName = screenName;
            return this;
        }

        public TwitterStatus setName(final String name) {
            this.name = name;
            return this;
        }

        public TwitterStatus setStatus(final String status) {
            this.status = status;
            return this;
        }

        public String getScreenName() {
            return screenName;
        }

        public String getName() {
            return name;
        }

        public String getStatus() {
            return status;
        }
    }
}
