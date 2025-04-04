package telekom.com.fidelityservice.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import telekom.com.fidelityservice.dto.UserDTO;
import telekom.com.fidelityservice.mapper.Mapper;
import telekom.com.fidelityservice.model.User;

@Component
public class FidelityMapper implements Mapper<User, UserDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public User mapTo(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    @Override
    public UserDTO mapFrom(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
