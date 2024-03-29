package co.lazuly.auth;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by boot on 19/12/2017.
 */
@Configuration
public class FeignConfigure {
    //    public static final int connectTimeOutMillis = 10;
//    public static final int readTimeOutMillis = 10;

    public static final int connectTimeOutMillis = 6000;
    public static final int readTimeOutMillis = 6000;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }
}
