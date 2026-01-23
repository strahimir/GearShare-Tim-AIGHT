package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.domain.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, UUID> {

    List<ReportEntity> findAllByReviewDateTimeIsNull();
}
