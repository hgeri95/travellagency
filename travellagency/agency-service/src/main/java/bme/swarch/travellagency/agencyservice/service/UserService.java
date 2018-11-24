package bme.swarch.travellagency.agencyservice.service;

import bme.swarch.travellagency.agencyservice.api.LoginRequest;
import bme.swarch.travellagency.agencyservice.api.UserDTO;
import bme.swarch.travellagency.agencyservice.exception.BadRequestException;
import bme.swarch.travellagency.agencyservice.model.User;
import bme.swarch.travellagency.agencyservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO createUser(UserDTO userDTO) {
        if (repository.findUserByUsername(userDTO.getUsername()) != null) {
            throw new BadRequestException("User already exists");
        } else {
            User user = repository.save(convertToEntity(userDTO));
            return convertToDTO(user);
        }
    }

    public boolean login(LoginRequest loginRequest) {
        User user = repository.findUserByUsername(loginRequest.getUsername());
        if (user != null) {
            return user.getPassword().equals(loginRequest.getPassword());
        } else {
            return false;
        }
    }

    public UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
