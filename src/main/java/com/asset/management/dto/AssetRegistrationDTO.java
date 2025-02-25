package com.asset.management.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetRegistrationDTO {
    private Long assetId;
    private String assetName;
    private String category;
}
