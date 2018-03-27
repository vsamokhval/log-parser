package org.vss.holder;

public class HystrixGroupAnalysis {
    private String groupName;
    private long maxDuration;
    private double averageReqPerSec;
    private int maxReqPerSec;
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

    public long getRequestCountWithError() {
        return requestCountWithError;
    }

    public void setRequestCountWithError(long requestCountWithError) {
        this.requestCountWithError = requestCountWithError;
    }

    @Override
    public String toString() {
        return String.format("Analysis for: %-30s max duration: %-8s | average request per sec: %-19s "
                             + "| max request per sec: %-3s | req count: %-7s | count with error %s",
                             getGroupName(), getMaxDuration(), getAverageReqPerSec(), getMaxReqPerSec(),
                             getRequestCount(), getRequestCountWithError());
    }
}
