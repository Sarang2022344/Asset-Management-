package com.asset.management.repository;

import com.asset.management.dto.TicketDTO;
import com.asset.management.model.MaintenanceLog;
import com.asset.management.model.Ticket;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByRaisedBy_EmployeeId(Long employeeId);


}
