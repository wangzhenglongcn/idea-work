package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


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
}
