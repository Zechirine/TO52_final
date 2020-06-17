package com.utbm.to52.banque.dao;
 
import com.utbm.to52.banque.entities.Operation;
import com.utbm.to52.banque.entities.Versement;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface IVersementRepository extends PagingAndSortingRepository<Versement, Long>, JpaSpecificationExecutor<Operation> {

    @Query("SELECT COUNT(v) FROM  Versement v")
    public  Long countVersement();

}
