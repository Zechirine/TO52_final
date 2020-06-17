package com.utbm.to52.banque.service;

import com.utbm.to52.banque.dao.*;
import com.utbm.to52.banque.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service  // annotation utilise pour les objets de la couche metier
@Transactional  // a importer : import org.springframework.transaction.annotation.Transactional;
public class ClientMetierImpl implements IClientMetier { //puis , on va faire le couplage faible avec la couche dao --> la couche metier va faire appel � la couche dao
    // pour faire l'injection de dependance  --> on va demander a spring d'injecter une implementation de cette interface

    @Autowired
    private IClientRepository clientRepository;


    public Page<Client> findByFilter(Map<String, String> filters, Pageable pageable) {
        return clientRepository.findAll(getFilterSpecification(filters), pageable);
    }

    @Override
    public Long countClients() {
        return clientRepository.count();
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    @Override
    public Client finfClientByName(String name) {
        try {
            return clientRepository.findClientByName(name).get(0);
        }catch (IndexOutOfBoundsException ex){
            return null;
        }
    }


    private Specification<Client> getFilterSpecification(Map<String, String> filterValues) {
        return (Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
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