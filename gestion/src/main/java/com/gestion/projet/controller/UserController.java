package com.gestion.projet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.projet.exception.ResourceNotFoundException;
import com.gestion.projet.models.User;
import com.gestion.projet.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserRepository repository;
	
	 @GetMapping("/users")
	  public List<User> getAllComptes() {
	    System.out.println("Get all Comptes...");
	    List<User> Comptes = new ArrayList<>();
	    repository.findAll().forEach(Comptes::add);
	    return Comptes;
	  }
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getCompteById(@PathVariable(value = "id") Long CompteId)
			throws ResourceNotFoundException {
		User user = repository.findById(CompteId)
				.orElseThrow(() -> new ResourceNotFoundException("Compte not found for this id :: " + CompteId));
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/users")
	public @Valid User createUser(@Valid @RequestBody User user) {
	
		return repository.save(user);
		 
	}

	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long id)
			throws ResourceNotFoundException {
		User user = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found  id :: " + id));
		repository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	 
	  @DeleteMapping("/users/delete")
	  public ResponseEntity<String> deleteAllUsers() {
	    System.out.println("Delete All Users...");
	    repository.deleteAll();
	    return new ResponseEntity<>("All Users have been deleted!", HttpStatus.OK);
	  }
	 
	  @PutMapping("/users/{id}")
	  public ResponseEntity<User> updateCompte(@PathVariable("id") long id, @RequestBody User user) {
	    System.out.println("Update Compte with ID = " + id + "...");
	    Optional<User> UserInfo = repository.findById(id);
	    if (UserInfo.isPresent()) {
	    	User user1= UserInfo.get();
	    	user1.setUsername(user.getUsername());
	      return new ResponseEntity<>(repository.save(user1), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }

}
