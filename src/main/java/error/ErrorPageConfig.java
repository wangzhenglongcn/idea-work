package error;


import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ErrorPageConfig {
    /* *
     * @param
     * @return org.springframework.boot.web.server.WebServerFactoryCustomizer<org.springframework.boot.web.server.ConfigurableWebServerFactory>
     * @author 王正龙
     * @creed: 异常拦截 （404,400,500）
     * @date 2019/12/13 16:25
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
        return factory -> {
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,
                    "/error/404");
            ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST,
                    "/error/404");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR,
                    "/error/500");
            factory.addErrorPages(errorPage400, errorPage404,
                    errorPage500);
        };
    }





}
