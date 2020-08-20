package com.gestion.projet.controller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gestion.projet.exception.ResourceNotFoundException;
import com.gestion.projet.models.Commande;
import com.gestion.projet.models.Compteur;
import com.gestion.projet.models.Lcomm;
import com.gestion.projet.repository.CommandeRepository;
import com.gestion.projet.repository.CompteurRepository;
import com.gestion.projet.repository.LcommRepository;

import java.time.LocalDate;  
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CommandeController {
	@Autowired 	CommandeRepository repository;
	@Autowired LcommRepository repo;
	@Autowired CompteurRepository comptrepo;
	@Autowired  ServletContext context;
	
	 @GetMapping("/Commandes")
	  public List<Commande> getAllCommandes() {
	    System.out.println("Get all Commandes...");
	    List<Commande> Commandes = new ArrayList<>();
	    repository.findAll().forEach(Commandes::add);
	    return Commandes;
	  }
	
	@GetMapping("/Commandes/{id}")
	public ResponseEntity<Commande> getCommandeById(@PathVariable(value = "id") Long CommandeId)
			throws ResourceNotFoundException {
		Commande Commande = repository.findById(CommandeId)
		
				.orElseThrow(() -> new ResourceNotFoundException("Commande not found for this id :: " + CommandeId));
		return ResponseEntity.ok().body(Commande);
	}
	
	@PostMapping("/Commandes")
	public ResponseEntity<Commande> createCommande(@Valid @RequestBody Commande Commande)  
			throws JsonParseException , JsonMappingException , Exception{
		  
		repository.save(Commande);
		List<Lcomm> Lcommandes = Commande.getLcomms();
	    for (Lcomm lc : Lcommandes) {
	        lc.setNumero(Commande.getNumero());
       		repo.save(lc);
	       }	 
	
     Optional<Compteur> CompteurInfo = comptrepo.findByAnnee(Commande.getAnnee());
     	if (CompteurInfo.isPresent()) {
	    	Compteur compteur = CompteurInfo.get();
	           compteur.setNumcomm(compteur.getNumcomm()+1);
	         compteur =   comptrepo.save(compteur);
     	}
		 return new ResponseEntity<>(HttpStatus.OK);
	}

	
	@DeleteMapping("/Commandes/{id}")
	public ResponseEntity<Commande>  deleteCommande(@PathVariable(value = "id") Long CommandeId)
	{
		Optional<Commande> CommandeInfo = repository.findById(CommandeId);
	  if (CommandeInfo.isPresent()) {
		  System.out.println("Commandeande 11");
		  Commande Commande = CommandeInfo.get();
		  repo.deleteByNumero(Commande.getNumero());
		  repository.delete(Commande);
		  return new ResponseEntity<>(HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	 
	  @DeleteMapping("/Commandes/delete")
	  public ResponseEntity<String> deleteAllCommandes() {
	    System.out.println("Delete All Commandes...");
	    repository.deleteAll();
	    return new ResponseEntity<>("All Commandes have been deleted!", HttpStatus.OK);
	  }
	 
	  @PutMapping("/Commandes/{id}")
	  public ResponseEntity<Commande> updateComm(@PathVariable("id") long id, @RequestBody Commande Comm) {
	    System.out.println("Update Comm with ID = " + id + "...");
	    Optional<Commande> CommInfo = repository.findById(id);
	    if (CommInfo.isPresent()) {
	    	Commande comm = CommInfo.get();
	    	comm.setLibelle(Comm.getLibelle());
	      return new ResponseEntity<>(repository.save(comm), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	  
	  
	
	 
	  }
	  
	  
		
		 
	}
