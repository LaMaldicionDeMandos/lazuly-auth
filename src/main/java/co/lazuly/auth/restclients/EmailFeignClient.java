package co.lazuly.auth.restclients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by boot on 15/12/2017.
 */
@FeignClient("lazuly-email")
public interface EmailFeignClient {
    @RequestMapping(method= RequestMethod.POST, value="/", consumes="application/json")
    int send(@RequestBody EmailRestClient.Email email, @RequestHeader("X-Authorization-Secret") String secret);
}
