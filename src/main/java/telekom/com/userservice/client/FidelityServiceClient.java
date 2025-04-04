package telekom.com.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import telekom.com.userservice.dto.UserDTO;

@FeignClient(name = "fidelity-service", url = "http://localhost:8082/fidelity")
public interface FidelityServiceClient {

    @PostMapping(path = "/addUser")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO);
}
