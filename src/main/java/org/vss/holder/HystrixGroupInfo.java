package org.vss.holder;

import org.vss.parser.Parser;

import java.util.Date;

public class HystrixGroupInfo {

    private String groupName;
    private Date startTime;
    private Date endTime;
    private boolean withError;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return endTime.getTime() - startTime.getTime();
    }

    public boolean isWithError() {
        return withError;
    }

    public void setWithError(boolean withError) {
        this.withError = withError;
    }

    @Override
    public String toString() {
        return String.format("%s | start: %s | end: %s | duration in millis: %-3s | with error: %s",
                             getGroupName(), Parser.DATE_FORMAT.format(getStartTime()),
                             Parser.DATE_FORMAT.format(getEndTime()), getDuration(), isWithError());
    }
}
