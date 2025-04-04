package telekom.com.userservice.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import telekom.com.userservice.dto.UserDTO;
import telekom.com.userservice.mapper.Mapper;
import telekom.com.userservice.model.User;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO mapTo(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User mapFrom(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
