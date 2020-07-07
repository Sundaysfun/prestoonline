package fileRead;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetSeed {
    public static void main(String[] args) {
        //文件路径
        String path = "";
        //文件名
        String fileName = "fileweb.txt";
        //搜索关键字
        String keyword = "href";
        File inputFile = new File(path + fileName);

        if (inputFile.exists()) {
            try {
                File outputFile = new File(path + "javaOutput_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt");
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
                String lineTxt = null;
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (lineTxt.contains("href")) {
                        int startIndex = lineTxt.indexOf("\"", lineTxt.indexOf("href")) + 1;
                        int endIndex = lineTxt.indexOf("\"", startIndex);
                        String href = lineTxt.substring(startIndex, endIndex);
                        if (!href.contains("?")&&!href.endsWith("/")) {
                            bufferedOutputStream.write(href.getBytes());
                            bufferedOutputStream.write("\r\n".getBytes());
                            bufferedOutputStream.flush();
                        }
                    }
                }
                bufferedOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
