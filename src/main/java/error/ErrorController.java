package error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    /* *
     * @param
     * @return java.lang.String
     * @author 王正龙
     * @creed: 前台报错跳转404
     * @date 2019/12/13 16:28
     */
    @RequestMapping("/404")
    public String to404() {
        return "error/404";
    }
    /* *
     * @param
     * @return java.lang.String
     * @author 王正龙
     * @creed: 后台报错跳转500
     * @date 2019/12/13 16:28
     */
    @RequestMapping("/500")
    public String to500() {
        return "error/500";
    }
}