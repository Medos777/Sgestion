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
	  public List<User> getAllUtilisateur() {
	    System.out.println("Get all Utilisateur...");
	 
	    List<User> User = new ArrayList<>();
	    repository.findAll().forEach(User::add);
	 
	    return User;
	  }
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUtilisateurById(@PathVariable(value = "id") Long UserId)
			throws ResourceNotFoundException {
		User User = repository.findById(UserId)
				.orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found for this id :: " + UserId));
		return ResponseEntity.ok().body(User);
	}

	 
	 @GetMapping("/users/5/{login}")
	  public   ResponseEntity<User> getUtilisateurByLogin(@PathVariable String id) 
		  throws ResourceNotFoundException {
		 User  user = repository.findById(id)
				  .orElseThrow(() -> new ResourceNotFoundException("Usernot found for this id : "));
		   return ResponseEntity.ok().body(user);
	  } 
	
	@PostMapping("/users")
	public User createUtilisateur(@Valid @RequestBody User user) {
		return repository.save(user);
	}
	

	@DeleteMapping("/users/{id}")
	public Map<String, Boolean> deleteUtilisateur(@PathVariable(value = "id") Long UserId)
			throws ResourceNotFoundException {
		User Utilisateur = repository.findById(UserId)
				.orElseThrow(() -> new ResourceNotFoundException("Utilisateur not found  id :: " + UserId));

		repository.delete(Utilisateur);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	  
	 
	  @DeleteMapping("/users/delete")
	  public ResponseEntity<String> deleteAllUtilisateur() {
	    System.out.println("Delete All Utilisateur...");
	 
	    repository.deleteAll();
	 
	    return new ResponseEntity<>("All Utilisateurs have been deleted!", HttpStatus.OK);
	  }
	 
	

	  @PutMapping("/users/{id}")
	  public ResponseEntity<User> updateUtilisateur(@PathVariable("id") long id, @RequestBody User Utilisateur) {
	    System.out.println("Update Utilisateur with ID = " + id + "...");
	 
	    Optional<User> UserInfo = repository.findById(id);
	 
	    if (UserInfo.isPresent()) {
	    	User User = UserInfo.get();
	    	User.setRole(User.getRole());
	    	User.setUsername(User.getUsername());
	    	User.setEmail(User.getEmail()); 
	    	User.setPwd(User.getPwd());
	      return new ResponseEntity<>(repository.save(User), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  }
}
