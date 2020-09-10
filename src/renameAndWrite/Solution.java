package renameAndWrite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/* 
Проход по дереву файлов
*/
public class Solution {
    File path = new File("train");
    File[] arrayFiles = path.listFiles();
    File resultFileAbsolutePath = new File(new File("result.txt").getAbsolutePath());

    List<String> listFiles = new ArrayList<>();
    Queue<String> queueDirectory = new ArrayDeque<>();

    public static void main(String[] args) {
        Solution solution = new Solution();

        checkedFiles(solution.listFiles, solution.queueDirectory, solution.arrayFiles);
        solution.resultFileAbsolutePath = renameFile(solution.resultFileAbsolutePath);
        solution.listFiles = sort(solution.arrayFiles);
        show(solution.listFiles, solution.queueDirectory);

        try {
            writeData(solution.resultFileAbsolutePath, solution.listFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(solution.resultFileAbsolutePath.getName());
    }

    public static void checkedFiles(List<String> list, Queue<String> queue, File[] srcFiles) {
        for (int i = 0; i < srcFiles.length; i++) {
            if (srcFiles[i].isDirectory()) {
                queue.offer(srcFiles[i].getPath());
            }

            if (srcFiles[i].isFile()) {
                list.add(srcFiles[i].getPath());
            }
        }
    }

    public static File renameFile(File file) {
        File allFilesContent = new File("allFilesContent.txt");

        if (FileUtils.isExist(file)) {
            FileUtils.renameFile(file, allFilesContent);
        }

        return allFilesContent;
    }

    public static List<String> sort(File[] srcFfiles) {
        List<String> sortedList = new ArrayList<>();

        for (int i = 0; i < srcFfiles.length; i++) {
            if (srcFfiles[i].length() <= 50) {
                sortedList.add(srcFfiles[i].getPath());
            }
        }

        Collections.sort(sortedList);

        return sortedList;
    }

    public static void show(List<String> list, Queue<String> queue) {
        System.out.println("Файлы ---------> ");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\t");
        }

        System.out.println("\nДиректории -----> ");
        for (int i = 0; i < queue.size(); i++) {
            System.out.println(queue.peek());
        }
    }

    public static void writeData(File file, List<String> srcFiles) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        for (int i = 0; i < srcFiles.size(); i++) {
            fileOutputStream.write(srcFiles.get(i).getBytes());
            fileOutputStream.write(System.lineSeparator().getBytes());
        }

        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
