package org.vss.saver;

import org.vss.holder.HystrixGroupInfo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResultSaver {
    private static final String FILENAME = "-parsed.log";
    public static final String FILE_NAME_SEPARATOR = "/";

    public void saveParsedFile(Map<String, List<HystrixGroupInfo>> groupsInfo, String folderName, String filePrefix) {
        try (final BufferedWriter bw =
                 new BufferedWriter(new FileWriter(folderName + filePrefix + FILENAME, false))) {

            for (Map.Entry<String, List<HystrixGroupInfo>> entry : groupsInfo.entrySet()) {
                for (HystrixGroupInfo info : entry.getValue()) {
                    bw.write(info.toString() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAnalysed(Map<String, String> analysed, String folderName, String filePrefix) {
        try (final BufferedWriter bw =
                 new BufferedWriter(new FileWriter(folderName + filePrefix + FILENAME, true))) {

            for (Map.Entry<String, String> entry : analysed.entrySet()) {
                bw.write(String.format("%s%n", entry.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFolderShortName(String folderName) {
        String folderShortName = folderName.endsWith(FILE_NAME_SEPARATOR)
                                 ? folderName.substring(0, folderName.length() - 1)
                                 : folderName;

        folderShortName = folderShortName.substring(folderShortName.lastIndexOf(FILE_NAME_SEPARATOR) + 1);
        return folderShortName;
    }
}
