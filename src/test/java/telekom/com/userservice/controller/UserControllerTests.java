package telekom.com.userservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import telekom.com.userservice.dto.UserDTO;
import telekom.com.userservice.mapper.Mapper;
import telekom.com.userservice.model.User;
import telekom.com.userservice.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Mock
    private UserService userService;
    @Mock
    private Mapper<User, UserDTO> userMapper;
    @InjectMocks
    private UserController userController;

    @Test
    public void testGetRegisteredUser() {
        String name = "test_registered";
        User user = new User();
        user.setId(9999999);
        user.setName(name);
        user.setPassword("hashed_password");
        user.setEmail("email");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(9999999);
        userDTO.setName(name);
        userDTO.setPassword("password");
        userDTO.setEmail("email");

        // Mock behavior
        when(userService.getUser(name)).thenReturn(Optional.of(user));
        when(userMapper.mapTo(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> foundUserDTO = userController.getUser(name);

        assertEquals(HttpStatus.OK, foundUserDTO.getStatusCode());
        assertEquals(userDTO, foundUserDTO.getBody());

        verify(userService, times(1)).getUser(name);
        verify(userMapper, times(1)).mapTo(user);
    }

    @Test
    public void testGetRegisteredUserNotFound() {
        String name = "test_registered";

        when(userService.getUser(name)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> foundUserDTO = userController.getUser(name);

        assertEquals(HttpStatus.NOT_FOUND, foundUserDTO.getStatusCode());
        assertNull(foundUserDTO.getBody());

        verify(userService, times(1)).getUser(name);
        verify(userMapper, times(0)).mapTo(any());
    }

    @Test
    public void testRegisterNewUser() {
        String name = "test_registered";
        User user = new User();
        user.setId(9999999);
        user.setName(name);
        user.setPassword("hashed_password");
        user.setEmail("email");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(9999999);
        userDTO.setName(name);
        userDTO.setPassword("password");
        userDTO.setEmail("email");

        when(userService.getUser(name)).thenReturn(Optional.empty());
        when(userMapper.mapFrom(userDTO)).thenReturn(user);
        when(userService.register(user)).thenReturn(Optional.of(user));
        when(userMapper.mapTo(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.register(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());

        verify(userService, times(1)).getUser(name);
        verify(userService, times(1)).register(user);
        verify(userMapper, times(1)).mapFrom(userDTO);
        verify(userMapper, times(1)).mapTo(user);
    }

    @Test
    public void testRegisterAlreadyRegisteredUser() {
        String name = "test_registered";
        User user = new User();
        user.setId(9999999);
        user.setName(name);
        user.setPassword("hashed_password");
        user.setEmail("email");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(9999999);
        userDTO.setName(name);
        userDTO.setPassword("password");
        userDTO.setEmail("email");

        when(userService.getUser(name)).thenReturn(Optional.of(user));
        when(userMapper.mapTo(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.register(userDTO);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(userDTO, response.getBody());

        verify(userService, times(1)).getUser(name);
        verify(userMapper, times(1)).mapTo(user);
    }
}
