package com.utbm.to52.banque.bean;

import com.utbm.to52.banque.datatable.CompteLazyDataModel;
import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Compte;
import com.utbm.to52.banque.entities.CompteCourant;
import com.utbm.to52.banque.entities.CompteEpargne;
import com.utbm.to52.banque.service.IClientMetier;
import com.utbm.to52.banque.service.ICompteMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@ManagedBean
@SessionScoped
public class CompteMbean implements Serializable {

    @Autowired
    ICompteMetier compteMetier;
    @Autowired
    IClientMetier clientMetier;



    private CompteLazyDataModel comptes;

    private String rechNom;
    private Client foundClient;
    private  List<Compte> comptesClient;
    private  String typeCompte;
    private Date dateCreation;
    private  Compte newCompte;
    private  double solde;
    private String codeCompte;


    @PostConstruct
    public  void init(){
        this.comptes = new CompteLazyDataModel(compteMetier);
        reset();
    }

    public CompteLazyDataModel getComptes() {
        return comptes;
    }

    public void clientAllCompte(){
        System.out.println("client"+rechNom);
        foundClient = clientMetier.finfClientByName(rechNom);
        if(foundClient!=null) {
            System.out.println("client" + foundClient.getNomClient());
            System.out.println("code" + foundClient.getCodeClient());
            comptesClient = compteMetier.findCompteByClient(foundClient);
            System.out.println("compte" + comptesClient);
        }
    }

    public String getRechNom() {
        return rechNom;
    }

    public void setRechNom(String rechNom) {
        this.rechNom = rechNom;
    }

    public List<Compte> getComptesClient() {
        return comptesClient;
    }

    public void setComptesClient(List<Compte> comptesClient) {
        this.comptesClient = comptesClient;
    }

    public  void reset(){
        rechNom = "";
        //comptesClient = new ArrayList<Compte>();
        dateCreation = new Date();
        solde = 0d;

    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(String typeCompte) {
        this.typeCompte = typeCompte;
    }

    public Compte getNewCompte() {
        return newCompte;
    }

    public void setNewCompte(Compte newCompte) {
        this.newCompte = newCompte;
    }

    public Client getFoundClient() {
        return foundClient;
    }

    public void setFoundClient(Client foundClient) {
        this.foundClient = foundClient;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public  void showCode(){
        if (typeCompte.equalsIgnoreCase("C")){
            codeCompte = compteMetier.generateCode("CC");
        }else{
            codeCompte = compteMetier.generateCode("CE");
        }
    }

    public  void save(){
        if (typeCompte.equalsIgnoreCase("C")){
            CompteCourant  compteCourant = new CompteCourant();
            compteCourant.setClient(foundClient);
            compteCourant.setSolde(solde);
            compteCourant.setDateCreation(dateCreation);
            compteCourant.setCodeCompte(codeCompte);
            compteMetier.save(compteCourant);
        }else{
            CompteEpargne compteEpargne = new CompteEpargne();
            compteEpargne.setClient(foundClient);
            compteEpargne.setSolde(solde);
            compteEpargne.setDateCreation(dateCreation);
            compteEpargne.setCodeCompte(codeCompte);
            compteMetier.save(compteEpargne);

        }
        clientAllCompte();
        reset();
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }
}
