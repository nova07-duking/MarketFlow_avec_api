package com.marketflow.repository;

import com.marketflow.model.GroupageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupageRepository extends JpaRepository<GroupageRequest, Long> {
    Page<GroupageRequest> findBySellerId(Long sellerId, Pageable pageable);
}
