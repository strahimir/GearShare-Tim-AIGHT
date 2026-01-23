package com.gearshare.gearshare.repositories.readOnly;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyRepository<T,ID> extends Repository<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    long count();


}
