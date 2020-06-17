package com.utbm.to52.banque.service;

import com.utbm.to52.banque.dao.IClientRepository;
import com.utbm.to52.banque.dao.ICompteRepository;
import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Compte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service  // annotation utilise pour les objets de la couche metier
@Transactional  // a importer : import org.springframework.transaction.annotation.Transactional;
public class compteMetierImpl implements ICompteMetier { //puis , on va faire le couplage faible avec la couche dao --> la couche metier va faire appel � la couche dao
    // pour faire l'injection de dependance  --> on va demander a spring d'injecter une implementation de cette interface

    @Autowired
    private ICompteRepository compteRepository;


    public Page<Compte> findByFilter(Map<String, String> filters, Pageable pageable) {
        return compteRepository.findAll(getFilterSpecification(filters), pageable);
    }

    @Override
    public List<Compte> findCompteByClient(Client client) {
        return compteRepository.findCompteByClient(client);
    }

    @Override
    public void save(Compte compte) {
        compteRepository.save(compte);
    }

    @Override
    public String generateCode(String type) {
        return type + compteRepository.count()+"0012020" ;
    }

    @Override
    public Compte findCompteByCode(String codeComtpte) {
        return compteRepository.findCompteByCode(codeComtpte);
    }


    private Specification<Compte> getFilterSpecification(Map<String, String> filterValues) {
        return (Root<Compte> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
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