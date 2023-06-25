/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package org.mutualser.contributor.repository;

import org.mutualser.contributor.entities.Affiliate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author MGonzalez
 */
public interface AffiliateRepository extends JpaRepository<Affiliate, Long> {
    
}
