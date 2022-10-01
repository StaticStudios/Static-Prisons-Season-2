package net.staticstudios.prisons.challenges;

public enum ChallengeDuration {
    HOURLY(60 * 60 * 1000),
    DAILY(24 * 60 * 60 * 1000),
    WEEKLY(7 * 24 * 60 * 60 * 1000);

    private final long durationInMillis;

    ChallengeDuration(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public long durationInMillis() {
        return durationInMillis;
    }
}
