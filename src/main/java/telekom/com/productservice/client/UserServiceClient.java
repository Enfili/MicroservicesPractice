package telekom.com.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import telekom.com.productservice.DTO.UserDTO;

@FeignClient(name = "user-service", url = "http://localhost:8081/user")
public interface UserServiceClient {

    @GetMapping(path = "/getUser")
    public ResponseEntity<UserDTO> getUser(@RequestParam("name") String name);
}
