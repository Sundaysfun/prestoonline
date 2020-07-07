package payload;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLEncoder;

/**
 * 字典批量加密md5
 * 暂时未做反爬虫处理，会被禁ip
 */
public class batchMD5 {
    public static void main(String[] args) {
        //字典路径
        String path = "F:test1";
        //输出路径
        String output = "F:test1.txt";
        //计数器
        int count = 0;

        File dir = new File(path);
        File[] payloads = dir.listFiles();
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(output)));
            for (int i = 0; i < payloads.length; i++) {
                //读取字典
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(payloads[i])));
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(URLEncoder.encode(lineTxt,"utf-8"));
                    //中间处理
                    HttpGet httpGet = new HttpGet("https://www.cmd5.com/hash.aspx?s=" + URLEncoder.encode(lineTxt,"utf-8"));
                    HttpEntity httpEntity = HttpClients.createDefault().execute(httpGet).getEntity();
                    String responseCode = EntityUtils.toString(httpEntity, "utf-8");
                    int startIndex = responseCode.indexOf("<font color='red'>sha256</font>") + 82;
                    int endIndex = responseCode.indexOf("<", startIndex);
                    String md5Str = responseCode.substring(startIndex, endIndex);
                    //写入txt
                    bufferedOutputStream.write(md5Str.getBytes());
                    bufferedOutputStream.write("\r\n".getBytes());
                    bufferedOutputStream.flush();
                    count++;
                }
            }
            bufferedOutputStream.close();
            System.out.println(count+"条数据已经处理完");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
