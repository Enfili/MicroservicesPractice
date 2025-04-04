package telekom.com.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import telekom.com.productservice.DTO.UserDTO;

@FeignClient(name = "fidelity-service", url = "http://localhost:8082/fidelity")
public interface FidelityServiceClient {

    @PutMapping(path = "/updateSpentMoney")
    public ResponseEntity<UserDTO> updateSpentMoney(@RequestParam int userId, @RequestParam double productPrice);
}
