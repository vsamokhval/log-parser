package org.vss.parser;

import static org.vss.HystrixGroupNames.SERVICE_MESSAGE_GROUP;
import static org.vss.HystrixGroupNames.GROUP_NAMES_WO_ET_GROUP;

import org.vss.holder.HystrixGroupInfo;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
    private static final String DATE_TIME_REGEX = "\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}";
    private static final Pattern PATTERN = Pattern.compile(DATE_TIME_REGEX);
    private static final String END_OF_EXTERNAL_REQUEST = "end call";

    private static final String START_OF_GROUP_REQUEST = "Start request";
    private static final String END_OF_GROUP_REQUEST = "End request";
    private static final String ERROR_TEMPLATE = "Exception";

    public Map<String, List<HystrixGroupInfo>> parseFiles(List<File> filesList) {
        boolean isReqStartFound = false;
        String currentGroupName = null;
        Map<String, List<HystrixGroupInfo>> groupsInfo = initGroupsInfo(GROUP_NAMES_WO_ET_GROUP);

        for(File file : filesList) {
            try (Scanner scanner = new Scanner(file)) {

                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (!isReqStartFound) {
                        HystrixGroupInfo requestInfo = tryToGetHystrixGroupInfo(line, GROUP_NAMES_WO_ET_GROUP);
                        if (requestInfo != null) {
                            currentGroupName = requestInfo.getGroupName();
                            groupsInfo.get(currentGroupName).add(requestInfo);
                            isReqStartFound = true;
                        }
                    } else {
                        if (line.contains(END_OF_EXTERNAL_REQUEST)) {
                            Date reqEndTime = getDateForMatchedLine(PATTERN, line);
                            List<HystrixGroupInfo> infoList = groupsInfo.get(currentGroupName);
                            HystrixGroupInfo requestInfo = infoList.get(infoList.size() - 1);
                            requestInfo.setEndTime(reqEndTime);
                            isReqStartFound = false;
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return groupsInfo;
    }

    public Map<String, List<HystrixGroupInfo>> parseFileForETGroup(List<File> filesList) {
        boolean isReqStartFound = false;
        Map<String, List<HystrixGroupInfo>> groupInfo = new HashMap<>();
        groupInfo.put(SERVICE_MESSAGE_GROUP.getName(), new ArrayList<>(10000));
        for(File file : filesList) {
            try (Scanner scanner = new Scanner(file)) {

                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (!isReqStartFound) {
                        HystrixGroupInfo requestInfo = tryToGetHystrixGroupInfo(line,
                                                                            Arrays.asList(START_OF_GROUP_REQUEST));
                        if (requestInfo != null) {
                            requestInfo.setGroupName(SERVICE_MESSAGE_GROUP.getName());
                            groupInfo.get(SERVICE_MESSAGE_GROUP.getName()).add(requestInfo);
                            isReqStartFound = true;
                        }
                    } else {
                        if (line.contains(END_OF_GROUP_REQUEST)) {
                            Date reqEndTime = getDateForMatchedLine(PATTERN, line);
                            List<HystrixGroupInfo> infoList = groupInfo.get(SERVICE_MESSAGE_GROUP.getName());
                            HystrixGroupInfo requestInfo = infoList.get(infoList.size() - 1);
                            requestInfo.setEndTime(reqEndTime);
                            requestInfo.setWithError(checkForError(line, ERROR_TEMPLATE));
                            isReqStartFound = false;
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return groupInfo;
    }

    private boolean checkForError(String line, String errorTemplate) {
        return line.contains(errorTemplate);
    }

    private Map<String,List<HystrixGroupInfo>> initGroupsInfo(List<String> groupNames) {
        Map<String, List<HystrixGroupInfo>> groupsInfo = new HashMap<>();
        groupNames.forEach(name -> groupsInfo.put(name, new ArrayList<HystrixGroupInfo>(10000)));
        return  groupsInfo;
    }

    private HystrixGroupInfo tryToGetHystrixGroupInfo(String line, List<String> groupNames) {
        Optional<String> groupName = groupNames.stream()
            .filter(line::contains)
            .findAny();

        if (groupName.isPresent()) {
            Date reqStartTime = getDateForMatchedLine(PATTERN, line);
            HystrixGroupInfo requestInfo = new HystrixGroupInfo();
            requestInfo.setGroupName(groupName.get());
            requestInfo.setStartTime(reqStartTime);
            return requestInfo;
        }
        return null;
    }

    private Date getDateForMatchedLine(Pattern pattern, String line) {
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        String matched = matcher.group();
        Date reqStartTime = null;
        try {
            reqStartTime = DATE_FORMAT.parse(matched);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Time not parsed");
        }

        return reqStartTime;
    }
}
