package net.staticstudios.prisons.challenges;

public enum ChallengeDuration {
    HOURLY(60 * 60 * 1000),
    DAILY(24 * 60 * 60 * 1000),
    WEEKLY(7 * 24 * 60 * 60 * 1000),
    MONTHLY(30L * 24 * 60 * 60 * 1000);

    private final long timeToLiveInMillis;

    ChallengeDuration(long timeToLiveInMillis) {
        this.timeToLiveInMillis = timeToLiveInMillis;
    }

    public long durationInMillis() {
        return timeToLiveInMillis;
    }
}
