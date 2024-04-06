package dev.arun.shoppingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.arun.shoppingapp.exception.UserNotFoundException;
import dev.arun.shoppingapp.model.User;
import dev.arun.shoppingapp.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
    	if(userRepository.existsById(id)) {
    		return true;
    	}
    	return false;
    }
    
    public User updateUser(Long id, User updatedUser) {
        // Check if the user exists
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            
            // Update user details
            existingUser.setName(updatedUser.getName());
            existingUser.setMobileNo(updatedUser.getMobileNo());
            existingUser.setAddress(updatedUser.getAddress());

            // Save the updated user
            return userRepository.save(existingUser);
        } else {
            // User not found, throw exception
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
}

