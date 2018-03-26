package org.register.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.register.model.User;
import org.register.model.UserDaoService;
import org.register.model.UserNotFoundException;
import org.register.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	//get all users
			@GetMapping("/users")
			public List<User> retrieveAllUsers() {
				return userRepository.findAll(); 
			}
			
			//get one user by id
			@GetMapping("/users/{email}/{password}")
			public Resource<User> retrieveUser(@PathVariable String email,
					@PathVariable String password) {
				Optional<User> user = userRepository.findByEmailAndPassword(email, password);
				if(!user.isPresent()) {
					throw new UserNotFoundException("email-"+ email);
				}
				Resource<User> resource = new Resource<User>(user.get());
				return resource; 
				
			}
			
			//register user
			@PostMapping("/register")
			public ResponseEntity<Object> registerUser(@Valid @RequestBody User user) {
				User savedUser = userRepository.save(user);
				
				URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getUserId()).toUri();
				
				return ResponseEntity.created(location).build();
			}
			
			//delete a user
			@DeleteMapping("/users/{id}")
			public void deleteby(@PathVariable int id) {
							
				userRepository.deleteById(id);
			
			}
}
