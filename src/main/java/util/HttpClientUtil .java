package util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.EncryptUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class HttpClientUtil  {


    /**
     * http  向我发送请求的接口
     * @return 发送的数据
     * @throws IOException
     */
    @RequestMapping("/sendTask")
    public @ResponseBody
    JSONArray sendTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EncryptUtils.getInstance();
        String requestString;
        String token = request.getHeader("token");
        requestString = getBodyString(request.getReader());
        JSONObject jsonObject = JSONObject.parseObject(requestString);
        String data = EncryptUtils.me.DESdecode(String.valueOf(jsonObject.get("data")), token);
        JSONArray jsonObjects = JSONArray.parseArray(data);
            if(jsonObjects.size() > 0){
                JSONObject dataObject2 = new JSONObject();
                dataObject2.put("state",404);
                dataObject2.put("total",0);
                response.setContentType("application/Json");
                try {
                    response.getWriter().write(JSONObject.toJSONString(dataObject2));
                    response.getWriter().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  jsonObjects;
            }
        return null;
    }


    public String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str;
    }


    public static void main(String[] arg) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String nowStr = now.format(formatter2);
        JSONObject params = new JSONObject();
        params.put("RobotId", 111);
        JSONObject param1 = new JSONObject();
        param1.put("collectTime",nowStr);
        JSONArray params2 = new JSONArray();
        JSONObject param21 = new JSONObject();
        param21.put("CO", "");
        param21.put("isAlarm", false);
        params2.add(param21);
        JSONObject param22 = new JSONObject();
        param22.put("CH4", "");
        param22.put("isAlarm", false);
        params2.add(param22);
        param1.put("gasData", params2);
        JSONObject param3 = new JSONObject();
        param3.put("data",45);
        param3.put("isAlarm", false);
        param1.put("infrared", param3);
        params.put("Data", param1);
        System.out.println(params);
        //callBgrsjk(params);
    }


    /**
     * http  post请求的接口
     * @return 发送的数据
     * @throws IOException
     */
    public static String callBgrsjk(JSONObject json,String url) {
        JSONObject jb=new JSONObject();
        jb.put("code",0);
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(300 * 1000)
                    .setConnectTimeout(300 * 1000)
                    .build();
            url = "http://URL:Port/地址";
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse response = httpClient.execute(post);
            String content = EntityUtils.toString(response.getEntity());
            System.out.println(content);
            return content;
        } catch (SocketTimeoutException e) {
            System.out.println("调用Dat+"
                    + ".aService接口超时,超时时间:" + 300
                    + "秒,url:" + url );
            return jb.toString();
        } catch (Exception e) {
            System.out.println("调用DataService接口失败,url:" + url );
            return jb.toString();
        }
    }

}
