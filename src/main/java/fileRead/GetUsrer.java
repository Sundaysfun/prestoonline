package fileRead;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GetUsrer {
    public static void main(String[] args) {
        //读取文件路径
        String path = "C:\\Users\\82010\\Desktop\\印度网站\\下载";

        Map<String, String> userMap = new HashMap<>();

        File dir = new File(path);
        File[] logs = dir.listFiles();
        try {
            File outputFile = new File("C:\\Users\\82010\\Desktop\\印度网站\\用户分析\\" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".txt");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
            for (int i = 0; i < logs.length; i++) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(logs[i])));
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String email = null;
                    String date = null;
                    Integer startIndex = null;
                    Integer endIndex = null;
                    if (lineTxt.contains("<loginEmailId>")) {
                        startIndex = lineTxt.indexOf("CDATA[", lineTxt.indexOf("<loginEmailId>")) + 6;
                        endIndex = lineTxt.indexOf("]", startIndex);
                        email = lineTxt.substring(startIndex, endIndex);
                        if (!email.equals("null") && lineTxt.contains("<dateJoin>")) {
                            startIndex = lineTxt.indexOf("CDATA[", lineTxt.indexOf("<dateJoin>")) + 6;
                            endIndex = lineTxt.indexOf("]", startIndex);
                            date = lineTxt.substring(startIndex, endIndex);
                        }
                        userMap.put(email, date);
                    }
                }
            }
            Iterator it = userMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                String lineText = "Emali:"+key+"  ~~~~~  joinDate:"+value+"\r\n";
                bufferedOutputStream.write(lineText.getBytes());
                bufferedOutputStream.flush();
            }
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
