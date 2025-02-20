package com.asset.management.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "asset_allocation")
public class AssetAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long allocationId;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private AssetRegistration asset;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate allocatedDate;

    private LocalDate returnedDate;

    @Column(nullable = false)
    private String status; // Assigned, Returned

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User performedBy;

    // Getters and setters...
    public long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(long allocationId) {
        this.allocationId = allocationId;
    }

    public AssetRegistration getAsset() {
        return asset;
    }

    public void setAsset(AssetRegistration asset) {
        this.asset = asset;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getAllocatedDate() {
        return allocatedDate;
    }

    public void setAllocatedDate(LocalDate allocatedDate) {
        this.allocatedDate = allocatedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
    }
}


