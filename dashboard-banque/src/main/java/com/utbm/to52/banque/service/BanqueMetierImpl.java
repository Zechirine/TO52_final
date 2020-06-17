package com.utbm.to52.banque.service;

import com.utbm.to52.banque.dao.*;
import com.utbm.to52.banque.entities.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service  // annotation utilise pour les objets de la couche metier
@Transactional  // a importer : import org.springframework.transaction.annotation.Transactional;
public class BanqueMetierImpl implements IBanqueMetier { //puis , on va faire le couplage faible avec la couche dao --> la couche metier va faire appel � la couche dao
  // pour faire l'injection de dependance  --> on va demander a spring d'injecter une implementation de cette interface
	@Autowired // a importer : import org.springframework.beans.factory.annotation.Autowired;
	private ICompteRepository compteRepository;

	@Autowired  
	private IOperationRepository operationRepository;

	@Autowired
	private IClientRepository clientRepository;

	@Autowired
	private IVersementRepository versementRepository;

	@Autowired
	private IRetraitRepository retraitRepository;

	 
	@Override
	public Compte getCompte(String codeCompte) {
		Compte compte=compteRepository.findCompteByCode(codeCompte);
		 if (compte==null) throw new RuntimeException("Compte introuvable"); // c'est une exception non surveiller
		return compte;
	}

	@Override
	public void versement(String codeCompte, double montant) {
		Compte compte = getCompte(codeCompte);
		Versement versement = new Versement(new Date(), montant,compte); // le versement est une operation
	    operationRepository.save(versement); // ici, la methode save() permet l'enregistrement
	    //mettre a jour le solde du compte
		System.out.println("compte ----------"+compte);
	    compte.setSolde(compte.getSolde() + montant);
	    compteRepository.save(compte); // ici, la methode save permet de mettre a jours le compte (update)  ---->Meme dans la console, on aura comme requette : Hibernate: update compte set code_cli=?, date_creation=?, solde=?, decouvert=? where code_compte=?
	 }

	@Override
	public void retrait(String codeCompte, double montant) {
		
		Compte compte = getCompte(codeCompte);
		double facilitesCaisse = 0;
		
		if (compte instanceof CompteCourant) {
			
			 facilitesCaisse = ((CompteCourant) compte).getDecouvert();
			
			 if ( compte.getSolde()+facilitesCaisse < montant )  throw new RuntimeException("Slode insuffisant");
			
		}
		
		Retrait retrait = new Retrait(new Date(), montant,compte); // le retrait est une operation
	    operationRepository.save(retrait); // ici, la methode save() permet l'enregistrement
	    //mettre a jour le solde du compte
	    compte.setSolde(compte.getSolde() - montant);
	    compteRepository.save(compte); // ici, la methode save permet de mettre a jours le compte (update)
	
		
	}

	@Override
	public void virement(String codeCompteRetrait, String codeCompteVersement, double montant) {
		if(codeCompteRetrait == codeCompteVersement)throw new RuntimeException("Impossible : On ne peut pas effectuer un virement dans le meme compte");
		retrait(codeCompteRetrait,montant);
		versement(codeCompteVersement,montant);
		
	}


   /*  @Override
	public List<Operation> listOperationsCompte(String codeCompte) {  // page: est le numero de la page
		 
		return operationRepository.listOperation(codeCompte);
	} */



	public Page<Operation> findByFilter(Map<String, String> filters, Pageable pageable) {
		return operationRepository.findAll(getFilterSpecification(filters), pageable);
	}

	@Override
	public Long countClients() {
		return clientRepository.count();
	}

	@Override
	public Long countDepot() {
		return versementRepository.countVersement();
	}

	@Override
	public Long countRetrait() {
		return retraitRepository.countRetrait();
	}

	@Override
	public Long countVersement() {
		return 0L;
	}

	private Specification<Operation> getFilterSpecification(Map<String, String> filterValues) {
		return (Root<Operation> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			Optional<Predicate> predicate = filterValues.entrySet().stream()
					.filter(v -> v.getValue() != null && v.getValue().length() > 0)
					.map(entry -> {
						Path<?> path = root;
						String key = entry.getKey();
						if (entry.getKey().contains(".")) {
							String[] splitKey = entry.getKey().split("\\.");
							path = root.join(splitKey[0]);
							key = splitKey[1];
						}
						return builder.like(path.get(key).as(String.class), "%" + entry.getValue() + "%");
					})
					.collect(Collectors.reducing((a, b) -> builder.and(a, b)));
			return predicate.orElseGet(() -> alwaysTrue(builder));
		};
	}

	private Predicate alwaysTrue(CriteriaBuilder builder) {
		return builder.isTrue(builder.literal(true));
	}

} 