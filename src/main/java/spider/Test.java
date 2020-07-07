package spider;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws URISyntaxException {
        //创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        //读取配置文件
        Properties properties = new Properties();
        HashMap<String, Object> config = new HashMap<>();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream("src\\main\\resources\\config.properties"));
            properties.load(in);
            Iterator<String> it = properties.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String next = it.next();
                config.put(next, properties.getProperty(next));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建get请求
        HttpGet httpGet = new HttpGet(new URI(null, null, null, Integer.parseInt(config.get("PORT").toString()), (String) (config.get("URL")), null, null));
        //HttpGet httpGet = new HttpGet("http://dudubird.xyz/");
        try {
            //执行get请求
            httpResponse = httpClient.execute(httpGet);
            //根据响应码处理
            if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                System.out.println("ok");
            }else {
                System.out.println("not ok");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
