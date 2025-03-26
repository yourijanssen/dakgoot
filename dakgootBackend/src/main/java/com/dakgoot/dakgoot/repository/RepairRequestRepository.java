package com.dakgoot.dakgoot.repository;

import com.dakgoot.dakgoot.model.RepairRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepairRequestRepository extends JpaRepository<RepairRequest, Long> {
	// Existing methods
	List<RepairRequest> findByHouseId(Long houseId);
	List<RepairRequest> findByCreatedById(Long userId);

	// Method to find repair requests with optional filtering
	@Query("SELECT r FROM RepairRequest r " +
			"WHERE (:status IS NULL OR r.status = :status) " +
			"AND (:repairType IS NULL OR r.repairType = :repairType)")
	List<RepairRequest> findByStatusAndRepairType(
			@Param("status") RepairRequest.RepairStatus status,
			@Param("repairType") String repairType
	);
}