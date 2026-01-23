package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.domain.entities.SuspensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface SuspensionRepository extends JpaRepository<SuspensionEntity, UUID> {

    List<SuspensionEntity> findAllBySuspensionLengthNotIn(List<Integer> values);
}
