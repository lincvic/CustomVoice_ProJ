
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.Scanner;

/**
 * Created by 王一疆 in 2018/7/29
 *
 * @email: lincvic@yahoo.com
 * @Description: CustomVoice pure Java Version 0.1
 */
public class Auth {

    private static HttpClient client =  HttpClientBuilder.create().build();
    private static Config config = new Config();

    private static void inputStream2File(InputStream inputStream) {
        /**
         *@Date: 20:37 2018/7/29
         *@Author: wang
         *@Description: 保存音频文件
         *@param: inputStream
         *@return: null
         */
        File audio = new File("output.wav");

        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(audio);

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            audio = null;
        }

    }

    private static String ssmlBuilder(String lang, String voiceName, String text) {
        /**
        *@Date: 21:01 2018/7/29
        *@Author: wang
        *@Description: 构造ssml
        *@param: lang 语言
        *@param: voiceName 声音
        *@param: text 要转换的语句
        *@return: ssml
        */
        String ssml = "<speak version='1.0' xmlns='http://www.w3.org/2001/10/synthesis' xmlns:mstts='http://www.w3.org/2001/mstts' xml:lang='%s'><voice name = '%s'>%s</voice></speak>";

        return String.format(ssml, lang, voiceName, text);


    }


    private static String inputStream2String(InputStream is) throws IOException {
        /**
         *@Date: 20:00 2018/7/29
         *@Author: wang
         *@Description: InputStream转String
         *@param: InputStream
         *@return: String
         */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    private static String Authentication() throws Exception {
        /**
         *@Date: 20:00 2018/7/29
         *@Author: wang
         *@Description: 获取Token
         *@param:null
         *@return: Token
         */
        HttpPost post = new HttpPost(config.getAUTH_HOST());
        post.setHeader("Ocp-Apim-Subscription-Key", config.getSUB_KEY());
        HttpResponse res = client.execute(post);
        HttpEntity entity = res.getEntity();

        return inputStream2String(entity.getContent());

    }

    private static int createRequests(String ssml) throws Exception {
        /**
         *@Date: 20:00 2018/7/29
         *@Author: wang
         *@Description: 请求Endpoint
         *@param: ssml
         *@return: status Code
         */
        HttpPost post = new HttpPost(config.getURL());
        StringEntity ssmlEntity = new StringEntity(ssml, "UTF-8");

        post.setHeader("ContentType", "application/ssml+xml");
        post.setHeader("X-MICROSOFT-OutputFormat", config.getENCODE_TYPE());
        post.setHeader("X-FD-ClientID", "RadioStationService");
        post.setHeader("X-FD-ImpressionGUID", config.getGUID());
        post.setHeader("User-Agent", "TTSClient");
        post.setHeader("Authorization", Authentication());
        post.setEntity(ssmlEntity);

        HttpResponse res = client.execute(post);
        HttpEntity entity = res.getEntity();
        int statusCode = res.getStatusLine().getStatusCode();
        Header contentType = entity.getContentType();

        inputStream2File(entity.getContent());

        System.out.println(contentType.toString());

        return statusCode;

    }


    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要转化为语音的句子：\n");
        String text = sc.nextLine();

        //创建ssml
        String ssml = ssmlBuilder("zh-CN", "wangyijiang1", text);

        int result = createRequests(ssml);
        System.out.println(result);

        if (result == 200) {
            System.out.println("音频已被成功创建");
        }





    }
}
