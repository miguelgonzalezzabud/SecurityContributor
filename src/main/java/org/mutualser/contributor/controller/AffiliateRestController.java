/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package org.mutualser.contributor.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.mutualser.contributor.entities.Affiliate;
import org.mutualser.contributor.repository.AffiliateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author MGonzalez
 */
@RestController
@RequestMapping("/affiliates")
public class AffiliateRestController {
    
    @Autowired
    AffiliateRepository affiliateRepository;
    
    @GetMapping()
    public List<Affiliate> list() {
        return affiliateRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Affiliate> get(@PathVariable long id) {
        return affiliateRepository.findById(id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody Affiliate input) {
        Affiliate find = affiliateRepository.findById(id).get();
        if(find != null){
            find.setDocument(input.getDocument());
            find.setName(input.getName());
        }
        Affiliate save = affiliateRepository.save(find);
        return ResponseEntity.ok(save);
        
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Affiliate input) {
        Affiliate save = affiliateRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional <Affiliate> findById = affiliateRepository.findById(id);
        if(findById.get() != null){
            affiliateRepository.delete(findById.get());
        }
            
        return ResponseEntity.ok().build();
    }
    
}
