package org.vss.analyzer;

import org.vss.holder.HystrixGroupAnalysis;
import org.vss.holder.HystrixGroupInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzer {

    private static final String FOLDER_NAME_KEY = "folderName";

    public Map<String, String> analyse(Map<String, List<HystrixGroupInfo>> groupsInfo, String filePrefix) {
        Map<String, String> analysed = new LinkedHashMap<>();
        analysed.put(FOLDER_NAME_KEY, filePrefix);

        for (Map.Entry<String, List<HystrixGroupInfo>> entry : groupsInfo.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                HystrixGroupAnalysis analysis = new HystrixGroupAnalysis();
                analysis.setGroupName(entry.getKey());
                analysis.setMaxDuration(calcMaxDuration(entry));
                calcAverageAndMaxRequestPerSec(entry, analysis);
                analysis.setRequestCount(entry.getValue().size());
                analysis.setRequestCountWithError(calcCountWithError(entry.getValue()));
                analysed.put(entry.getKey(), analysis.toString());
            }
        }

        return analysed;
    }

    private long calcCountWithError(List<HystrixGroupInfo> infoList) {
        return infoList.stream().filter(HystrixGroupInfo::isWithError).count();
    }

    private void calcAverageAndMaxRequestPerSec(Map.Entry<String, List<HystrixGroupInfo>> entry,
                                                HystrixGroupAnalysis analysis) {
        List<Integer> countReqPerSecond = new ArrayList<>();
        int count = 1;
        long requestStartTime = entry.getValue().get(0).getStartTime().getTime();
        for (HystrixGroupInfo info : entry.getValue()) {
            if (Math.abs(requestStartTime - info.getStartTime().getTime()) < 1000) {
                count += 1;
            } else {
                countReqPerSecond.add(count);
                count = 1;
                requestStartTime = info.getStartTime().getTime();
            }
        }
        int max = countReqPerSecond.stream().collect(Collectors.summarizingInt(Integer::intValue)).getMax();
        double average = countReqPerSecond.stream().collect(Collectors.summarizingInt(Integer::intValue)).getAverage();
        analysis.setMaxReqPerSec(max);
        analysis.setAverageReqPerSec(average);
    }

    private long calcMaxDuration(Map.Entry<String, List<HystrixGroupInfo>> entry) {
        return entry.getValue().stream().collect(Collectors.summarizingLong(HystrixGroupInfo::getDuration))
            .getMax();
    }
}
