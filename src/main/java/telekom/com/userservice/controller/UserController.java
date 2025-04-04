package telekom.com.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import telekom.com.userservice.client.FidelityServiceClient;
import telekom.com.userservice.dto.UserDTO;
import telekom.com.userservice.mapper.Mapper;
import telekom.com.userservice.model.User;
import telekom.com.userservice.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Mapper<User, UserDTO> userMapper;

    private final FidelityServiceClient fidelityServiceClient;

    public UserController(FidelityServiceClient fidelityServiceClient) {
        this.fidelityServiceClient = fidelityServiceClient;
    }

    @GetMapping(path = "/getUser")
    public ResponseEntity<UserDTO> getUser(@RequestParam String name) {
        Optional<User> user = userService.getUser(name);

        if (user.isPresent()) {
            return new ResponseEntity<>(userMapper.mapTo(user.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        Optional<User> alreadyRegisteredUser = userService.getUser(userDTO.getName());

        if (alreadyRegisteredUser.isPresent()) {
            return new ResponseEntity<>(userMapper.mapTo(alreadyRegisteredUser.get()), HttpStatus.CONFLICT);
        }

        User user = userMapper.mapFrom(userDTO);

        // hash the password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Optional<User> userCreated = userService.register(user);

        if (userCreated.isPresent()) {
            fidelityServiceClient.addUser(userDTO);

            return new ResponseEntity<>(userMapper.mapTo(userCreated.get()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
