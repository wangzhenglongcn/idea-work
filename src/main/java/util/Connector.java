package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class Connector {
	
/**
 * 发送HttpPost请求
 *
 * @param strURL  服务地址
 * @param params  json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
 * @return 成功:返回json字符串<br/>
 */

public static String post(String strURL, String params) {
    BufferedReader reader;
    try {
        URL url = new URL(strURL);// 创建连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST"); // 设置请求方式
        // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式<br>、　　　　　　//因为要登陆才可以执行请求，所以这里要带cookie的header
        connection.setRequestProperty("Cookie", "geli-session=41b6d86db1e97df5:-60813064:16d0571e3e8:-3a58377431669b192d08; c=58ydwry; u=58zqg8z; pcsuv=1482230765738.a.46958170; u4ad=4031lfgby; UM_distinctid=16be539fa796b9-099f04a8a5445d-4144032b-1fa400-16be539fa7a7d3; visitedfid=22035D16260D14152; gr_user_id=b2a6251c-dddb-4502-91f9-0b6f10ccb260; favCar=%E5%A5%A5%E8%BF%AAA3_9550%7C%E5%A5%A5%E8%BF%AAA8L_7%7C%E7%A6%8F%E5%85%8B%E6%96%AFActive_25101%7C%E8%BD%A9%E9%80%B8_3996%7C%E5%88%9B%E7%95%8C_24483; locationWap=%7B%22expires%22%3A1568884707863%2C%22city%22%3A%22%E5%93%88%E5%B0%94%E6%BB%A8%E5%B8%82%22%2C%22cityUser%22%3A%22%E5%93%88%E5%B0%94%E6%BB%A8%E5%B8%82%22%2C%22cityCode%22%3A%22230100%22%2C%22proCode%22%3A%22230000%22%2C%22cityCodeUser%22%3A%22230100%22%2C%22proCodeUser%22%3A%22230000%22%7D; AplocationWap=%7B%22regionId%22%3A187%2C%20%22regionName%22%3A%22%E5%93%88%E5%B0%94%E6%BB%A8%22%7D; pcLocate=%7B%22proCode%22%3A%22440000%22%2C%22pro%22%3A%22%E5%B9%BF%E4%B8%9C%E7%9C%81%22%2C%22cityCode%22%3A%22441900%22%2C%22city%22%3A%22%E4%B8%9C%E8%8E%9E%E5%B8%82%22%2C%22dataType%22%3A%22user%22%2C%22expires%22%3A1571888929008%7D; pcautoLocate=%7B%22proId%22%3A5%2C%22cityId%22%3A6%2C%22url%22%3A%22%2F%2Fwww.pcauto.com.cn%2Fqcbj%2Fdg%2F%22%2C%22dataTypeAuto%22%3A%22user%22%7D; PClocation=6; pcuvdata=lastAccessTime=1570869719999|visits=289; channel=9396");
        connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
        connection.connect();
        //发送数据
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
        out.append(params);
        out.flush();
        out.close();
        //一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
        // 读取响应
        reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        String res = "";
        while ((line = reader.readLine()) != null) {
            res += line;
        }
        reader.close();
        res = res.replaceAll("null","");
        System.out.println("+++++++++++++++"+res);
        return res;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "error"; // 自定义错误信息
}
 
/**
 * http    接口
 * @return 接收的数据
 * @throws IOException
 */

public void sendTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String requestString;
    requestString = getBodyString(request.getReader());
    JSONObject jsonObjects = JSONObject.parseObject(requestString);
    if(jsonObjects.size() > 0){
    	System.out.println("+++++++++++"+jsonObjects);
        JSONObject dataObject2 = new JSONObject();
        dataObject2.put("state",404);
        dataObject2.put("total",0);
        response.setContentType("application/Json");
        response.setCharacterEncoding("UTF-8");
        System.out.println(JSONObject.toJSONString(dataObject2));
        try {
            response.getWriter().write(JSONObject.toJSONString(dataObject2));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }else {
    	System.out.println("+++++++++++"+"出错了");
    	 return ;
    }
   
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

 
public static void main(String[] args){
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
      Connector.post("http://127.0.0.1:8145/copyright/Connector/sendTask",params.toJSONString());
}


}
