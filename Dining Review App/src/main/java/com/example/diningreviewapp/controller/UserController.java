package com.example.diningreviewapp.controller;

import com.example.diningreviewapp.DTO.UserDto;
import com.example.diningreviewapp.repository.UserRepository;
import com.example.diningreviewapp.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserRepository userRepository;
    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping()
    public Iterable<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        for (User u: this.userRepository.findAll()){
            userDtos.add(User.EntityToDto(u));
        }
        return userDtos;
    }
    @PostMapping()
    public User createNewUser(@RequestBody User user) {
        return this.userRepository.save(user);
    }
    @PostMapping("/users")
    public Iterable<User> createNewUsers(@RequestBody List<User> users) {
        return this.userRepository.saveAll(users);
    }
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable("id") Integer id, @RequestBody User u) {
        Optional<User> userToUpdateOptional = this.userRepository.findById(id);
        if (userToUpdateOptional.isEmpty()) return null;

        // Since isPresent() was true, we can .get() the User object out of the Optional <<<<------
        User userToUpdate = userToUpdateOptional.get();

        if (u.getName() != null)  userToUpdate.setName(u.getName());
        if (u.getCity() != null) userToUpdate.setCity(u.getCity());
        if (u.getState() != null) userToUpdate.setState(u.getState());
        if (u.getZipcode() != null) userToUpdate.setZipcode(u.getZipcode());
        if (u.getPeanut_allergies() != null) userToUpdate.setPeanut_allergies(u.getPeanut_allergies());
        if (u.getEgg_allergies() != null) userToUpdate.setEgg_allergies(u.getEgg_allergies());
        if (u.getDairy_allergies() != null) userToUpdate.setDairy_allergies(u.getDairy_allergies());

        return User.EntityToDto(this.userRepository.save(userToUpdate));
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable("id") Integer id) {
        Optional<User> userToDeleteOptional = this.userRepository.findById(id);
        if (userToDeleteOptional.isEmpty()) return null;

        User userToDelete = userToDeleteOptional.get();
        UserDto userdto = User.EntityToDto(userToDelete);
        this.userRepository.delete(userToDelete);
        return userdto;
    }
}
