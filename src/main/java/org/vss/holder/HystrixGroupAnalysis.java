package org.vss.holder;

public class HystrixGroupAnalysis {
    private String groupName;
    private long maxDuration;
    private long duration99Percentile;
    private double averageReqPerSec;
    private int maxReqPerSec;
    private long maxReqPerSecCount;
    private int requestCount;
    private long requestCountWithError;

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public long getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(long maxDuration) {
        this.maxDuration = maxDuration;
    }

    public long getDuration99Percentile() {
        return duration99Percentile;
    }

    public void setDuration99Percentile(long duration99Percentile) {
        this.duration99Percentile = duration99Percentile;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public double getAverageReqPerSec() {
        return averageReqPerSec;
    }

    public void setAverageReqPerSec(double averageReqPerSec) {
        this.averageReqPerSec = averageReqPerSec;
    }

    public int getMaxReqPerSec() {
        return maxReqPerSec;
    }

    public void setMaxReqPerSec(int maxReqPerSec) {
        this.maxReqPerSec = maxReqPerSec;
    }

    public long getMaxReqPerSecCount() {
        return maxReqPerSecCount;
    }

    public void setMaxReqPerSecCount(long maxReqPerSecCount) {
        this.maxReqPerSecCount = maxReqPerSecCount;
    }

    public long getRequestCountWithError() {
        return requestCountWithError;
    }

    public void setRequestCountWithError(long requestCountWithError) {
        this.requestCountWithError = requestCountWithError;
    }

    @Override
    public String toString() {
        return String.format("Analysis for: %-30s max duration: %-4s | 99 perc dur: %-4s "
                             + "| average request per sec: %-19s | max request per sec: %-3s | countMax: %-4s "
                             + "| req count: %-7s | count with error %s",
                             getGroupName(), getMaxDuration(), getDuration99Percentile() ,getAverageReqPerSec(),
                             getMaxReqPerSec(), getMaxReqPerSecCount(), getRequestCount(), getRequestCountWithError());
    }
}
