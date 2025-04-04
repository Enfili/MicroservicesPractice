package telekom.com.fidelityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import telekom.com.fidelityservice.dto.UserDTO;
import telekom.com.fidelityservice.mapper.Mapper;
import telekom.com.fidelityservice.model.User;
import telekom.com.fidelityservice.service.FidelityService;

import java.util.Optional;

@RestController
@RequestMapping("/fidelity")
public class FidelityController {

    @Autowired
    private FidelityService fidelityService;
    @Autowired
    private Mapper<User, UserDTO> mapper;


    @PostMapping(path = "/addUser")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        Optional<User> alreadyExists = fidelityService.findById(userDTO.getId());

        if (alreadyExists.isPresent()) {
            return new ResponseEntity<>(mapper.mapFrom(alreadyExists.get()), HttpStatus.CONFLICT);
        }

        User user = mapper.mapTo(userDTO);
        Optional<User> savedUser = fidelityService.save(user);

        if (savedUser.isPresent()) {
            return new ResponseEntity<>(mapper.mapFrom(savedUser.get()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/updateSpentMoney")
    public ResponseEntity<UserDTO> updateSpentMoney(@RequestParam int userId, @RequestParam double productPrice) {
        Optional<User> foundUser = fidelityService.findById(userId);

        if (foundUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<User> updatedUser = fidelityService.updateUser(foundUser.get(), productPrice);

        if (updatedUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(mapper.mapFrom(updatedUser.get()), HttpStatus.OK);
    }
}
