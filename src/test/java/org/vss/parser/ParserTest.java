package org.vss.parser;

import org.vss.analyzer.LogAnalyzer;
import org.vss.holder.HystrixGroupInfo;
import org.vss.saver.ResultSaver;
import org.vss.utils.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

class ParserTest {

    @Test
    public void parseFiles() {
        Parser suit = new Parser();
        LogAnalyzer analyzer = new LogAnalyzer();
        ResultSaver saver = new ResultSaver();
        String folderName = "path/to/files";
        String writeFolderName = "path/to/store/result/";

        Map<String, List<HystrixGroupInfo>> resultMap = suit.parseFiles(
            FileUtils.getFilesAndFilesSubDirectories(folderName));

        String filePrefix = saver.getFolderShortName(folderName);
        Map<String, String> analysed = analyzer.analyse(resultMap, filePrefix);
        saver.saveParsedFile(resultMap, writeFolderName, filePrefix);
        saver.saveAnalysed(analysed, writeFolderName, filePrefix);
        System.out.println("prefix: " + filePrefix);
    }

    @Test
    public void parseFilesForETGroup() {
        Parser suit = new Parser();
        LogAnalyzer analyzer = new LogAnalyzer();
        ResultSaver saver = new ResultSaver();
        String folderName = "path/to/files";
        String writeFolderName = "path/to/store/result/";

        Map<String, List<HystrixGroupInfo>> resultForETMap =
            suit.parseFileForETGroup(FileUtils.getFilesAndFilesSubDirectories(folderName));

        String filePrefix = saver.getFolderShortName(folderName);
        Map<String, String> analysed = analyzer.analyse(resultForETMap, filePrefix);
        saver.saveParsedFile(resultForETMap, writeFolderName, filePrefix);
        saver.saveAnalysed(analysed, writeFolderName, filePrefix);
        System.out.println("prefix: " + filePrefix);
    }

    @Test
    public void shouldGetFilesFromFolder() {
        String folderName = "path/to/files";

        List<File> fileList = FileUtils.getFilesAndFilesSubDirectories(folderName);
        fileList.forEach(file -> System.out.println(file.getName() +
                                                    " | " + file.getAbsolutePath()));
    }

    @Test
    public void shouldReturnFolderShortName() {
        ResultSaver saver = new ResultSaver();
        String folderName = "C:/path/to/files/folder/";
        String actual = saver.getFolderShortName(folderName);
        Assertions.assertEquals("folder", actual);

        actual = saver.getFolderShortName(folderName.substring(0, folderName.length() - 1));
        Assertions.assertEquals("folder", actual);
    }
}