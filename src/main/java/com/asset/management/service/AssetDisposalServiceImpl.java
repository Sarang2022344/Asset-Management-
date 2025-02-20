package com.asset.management.service;

import com.asset.management.dto.AssetDisposalDTO;
import com.asset.management.model.AssetDisposal;
import com.asset.management.model.AssetRegistration;
import com.asset.management.model.Company;
import com.asset.management.repository.AssetDisposalRepository;
import com.asset.management.repository.AssetRegistrationRepository;
import com.asset.management.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class AssetDisposalServiceImpl implements AssetDisposalService {
    private final AssetDisposalRepository disposalRepository;
    private final AssetRegistrationRepository assetRepository;
    private final CompanyRepository companyRepository;

    public AssetDisposalServiceImpl(AssetDisposalRepository disposalRepository,AssetRegistrationRepository assetRepository, CompanyRepository companyRepository) {
        this.disposalRepository = disposalRepository;
        this.assetRepository = assetRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public AssetDisposalDTO disposeAsset(AssetDisposalDTO disposalDTO) {
        AssetRegistration asset = assetRepository.findById(disposalDTO.getAssetId())
                .orElseThrow(() -> new IllegalArgumentException("Asset not found"));

        Company company = companyRepository.findById(disposalDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        AssetDisposal disposal = AssetDisposal.builder()
                .asset(asset)
                .company(company)
                .disposalDate(disposalDTO.getDisposalDate())
                .reason(disposalDTO.getReason())
                .build();

        disposalRepository.save(disposal);

        return new AssetDisposalDTO(
                disposal.getDisposalId(),
                disposal.getAsset().getAssetId(),
                disposal.getCompany().getCompanyId(),
                disposal.getDisposalDate(),
                disposal.getReason()
        );
    }

    @Override
    public AssetDisposalDTO updateDisposal(Long disposalId, AssetDisposalDTO disposalDTO) {
        AssetDisposal disposal = disposalRepository.findById(disposalId)
                .orElseThrow(() -> new RuntimeException("Asset Disposal record not found"));

        AssetRegistration asset = assetRepository.findById(disposalDTO.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        Company company = companyRepository.findById(disposalDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));


        disposal.setAsset(asset);
        disposal.setCompany(company);
        disposal.setDisposalDate(disposalDTO.getDisposalDate());
        disposal.setReason(disposalDTO.getReason());

        disposalRepository.save(disposal);

        return new AssetDisposalDTO(
                disposal.getDisposalId(),
                disposal.getAsset().getAssetId(),
                disposal.getCompany().getCompanyId(),
                disposal.getDisposalDate(),
                disposal.getReason()
        );
    }

}
