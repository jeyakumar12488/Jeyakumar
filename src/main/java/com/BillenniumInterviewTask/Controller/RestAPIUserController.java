package com.BillenniumInterviewTask.Controller;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BillenniumInterviewTask.Entity.RestAPIUser;
import com.BillenniumInterviewTask.Exception.ResourceNotFoundException;
import com.BillenniumInterviewTask.Repository.RestAPIUserRepository;

@RestController
@RequestMapping("/api/users")
public class RestAPIUserController {

	@Autowired
	private RestAPIUserRepository repository;

	@GetMapping
	public List<RestAPIUser> getAllUsers() {
		return this.repository.findAll();
	}

	@GetMapping("/{id}")
	public RestAPIUser getUserById(@PathVariable(value = "id") long userId) {
		return this.repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user  not found with id:" + userId));
	}

	@GetMapping("/name")
	public List<RestAPIUser> getUserByUserName(@RequestParam("firstName") String firstName) {
		return this.repository.findByFirstName(firstName);
	}

	@PostMapping
	public ResponseEntity createUser(@Valid @RequestBody RestAPIUser user) {

		List<RestAPIUser> validUser = repository.validateUser(user.getEmail());
		if (CollectionUtils.isEmpty(validUser)) {
			this.repository.save(user);
			return new ResponseEntity<>("New User created successfully", HttpStatus.OK);
		} else {
			String errorMsg = "User Already Exists please change emailaddress";
			return new ResponseEntity<>(errorMsg, HttpStatus.OK);
		}

	}

	@PutMapping("/{id}")
	public RestAPIUser updateUser(@RequestBody RestAPIUser user, @PathVariable("id") long userId) {
		RestAPIUser existingUser = this.repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user  not found with id:" + userId));

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		return this.repository.save(existingUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<RestAPIUser> deleteUser(@PathVariable("id") long userId) {
		RestAPIUser existingUser = this.repository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user  not found with id:" + userId));

		this.repository.delete(existingUser);
		return ResponseEntity.ok().build();
	}
}