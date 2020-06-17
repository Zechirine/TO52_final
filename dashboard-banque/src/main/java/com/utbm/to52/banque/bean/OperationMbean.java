package com.utbm.to52.banque.bean;

import com.utbm.to52.banque.datatable.CompteLazyDataModel;
import com.utbm.to52.banque.entities.Client;
import com.utbm.to52.banque.entities.Compte;
import com.utbm.to52.banque.entities.CompteCourant;
import com.utbm.to52.banque.entities.CompteEpargne;
import com.utbm.to52.banque.service.IBanqueMetier;
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
public class OperationMbean implements Serializable {

    @Autowired
    ICompteMetier compteMetier;
    @Autowired
    IClientMetier clientMetier;

    @Autowired
    IBanqueMetier banqueMetier;

    private CompteLazyDataModel comptes;

    private String rechNom;
    private Client foundClient;
    private  List<Compte> comptesClient;
    private  String typeCompte;
    private Date dateCreation;
    private  Compte newCompte;
    private  double solde;
    private String codeCompte;
    private Compte selectedCompte;
    private double montantDepot;
    private  double montantRetrait;
    private double nvoSolde;


    @PostConstruct
    public  void init(){
        this.comptes = new CompteLazyDataModel(compteMetier);
        reset();
    }

    public CompteLazyDataModel getComptes() {
        return comptes;
    }

    public void clientAllCompte(){
        comptesClient = new ArrayList<>();
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
        comptesClient = new ArrayList<Compte>();
        solde = 0d;
        montantDepot = 0d;
        montantRetrait = 0d;
        //selectedCompte = new Compte() ;
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

    public Compte getSelectedCompte() {
        return selectedCompte;
    }

    public void setSelectedCompte(Compte selectedCompte) {
        this.selectedCompte = selectedCompte;
    }

    public  void showCode(){
        if (typeCompte.equalsIgnoreCase("C")){
            codeCompte = compteMetier.generateCode("CC");
        }else{
            codeCompte = compteMetier.generateCode("CE");
        }
    }

    public double getMontantDepot() {
        return montantDepot;
    }

    public void setMontantDepot(double montantDepot) {
        this.montantDepot = montantDepot;
    }

    public double getMontantRetrait() {
        return montantRetrait;
    }

    public void setMontantRetrait(double montantRetrait) {
        this.montantRetrait = montantRetrait;
    }

    public  void retrait(){
        nvoSolde =0d;
        banqueMetier.retrait(selectedCompte.getCodeCompte(),montantDepot);
        nvoSolde = compteMetier.findCompteByCode(selectedCompte.getCodeCompte()).getSolde();
        reset();
    }

    public  void depot(){
        nvoSolde =0d;
        banqueMetier.versement(selectedCompte.getCodeCompte(),montantDepot);
        nvoSolde = compteMetier.findCompteByCode(selectedCompte.getCodeCompte()).getSolde();
        reset();
    }

    public double getNvoSolde() {
        return nvoSolde;
    }

    public void setNvoSolde(double nvoSolde) {
        this.nvoSolde = nvoSolde;
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }
}
