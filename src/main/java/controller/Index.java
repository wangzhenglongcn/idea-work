package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.EncryptUtils;

import java.io.BufferedReader;
import java.io.IOException;


@Controller
public class Index {

    @Autowired
    UserMapper UserMapper;

   /* *
    * @param model
    * @param response
    * @return java.lang.String
    * @author 王正龙
    * @creed: 配置启动首页
    * @date 2019/12/12 16:23
    */
    @RequestMapping("/")
    public String index(Model model, HttpServletResponse response) {
        model.addAttribute("name", "simonsfan");
        return "/login";
    }

    @GetMapping("/index")
    String test(HttpServletRequest request) {
        //逻辑处理
        request.setAttribute("key", "hello world");
        return "/index";
    }

    @GetMapping("/dashboard")
    String test1(HttpServletRequest request) {
        return "/dashboard";
    }



    @RequestMapping("show/{id}")
    public String test(@PathVariable int id,HttpServletRequest request){
        request.setAttribute("key",UserMapper.selectUserById(id).toString());
        return "/show";
    }

    /**
     * http  向我发送请求的接口
     * @return 发送的数据
     * @throws IOException
     */
    @RequestMapping(value = "sendTask")
    public @ResponseBody
    JSONObject sendTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EncryptUtils.getInstance();
        String requestString;
        String token = request.getHeader("token");
        requestString = getBodyString(request.getReader());
        JSONObject jsonObjects = JSONObject.parseObject(requestString);
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
}
