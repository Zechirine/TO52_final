package com.utbm.to52.banque.dao;
 
import com.utbm.to52.banque.entities.Operation;
import com.utbm.to52.banque.entities.Retrait;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.LongFunction;


@Repository
@Transactional
public interface IRetraitRepository extends PagingAndSortingRepository<Retrait, Long>, JpaSpecificationExecutor<Operation> {

    @Query("SELECT COUNT(r) FROM  Retrait r")
    public Long countRetrait();
}
