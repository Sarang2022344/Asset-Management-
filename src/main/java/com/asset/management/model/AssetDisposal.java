package com.asset.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "disposal")
public class AssetDisposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disposalId;

    @OneToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private AssetRegistration asset;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private LocalDate disposalDate;
    private String reason;
    @PrePersist
    public void setDisposalDateBeforePersist() {
        if (this.disposalDate == null) {
            this.disposalDate = LocalDate.now();
        }
    }
}
