package com.asset.management.service;

import com.asset.management.dto.AssetDTO;
import com.asset.management.exception.AssetNotFoundException;
import com.asset.management.exception.InvalidAssetException;

import com.asset.management.model.AssetRegistration;

import com.asset.management.repository.AssetRegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssetRegistrationServiceImplTest {

    @Mock
    private AssetRegistrationRepository assetRegistrationRepository;

    @InjectMocks
    private AssetRegistrationServiceImpl assetRegistrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAssets() {
        when(assetRegistrationRepository.findAll()).thenReturn(List.of(new AssetRegistration()));

        List<AssetDTO> result = assetRegistrationService.getAllAssets();

        assertFalse(result.isEmpty());
        verify(assetRegistrationRepository, times(1)).findAll();
    }





    @Test
    void testGetAssetById_NotFound() {
        Long assetId = 1L;
        when(assetRegistrationRepository.findById(assetId)).thenReturn(Optional.empty());

        assertThrows(InvalidAssetException.class, () -> assetRegistrationService.getAssetById(assetId));
    }


    @Test
    void testDeleteAssetById_Success() {
        Long assetId = 1L;
        AssetRegistration asset = new AssetRegistration();
        asset.setAssetId(assetId);

        when(assetRegistrationRepository.findById(assetId)).thenReturn(Optional.of(asset));
        doNothing().when(assetRegistrationRepository).deleteById(assetId); // Mock deleteById

        assetRegistrationService.deleteAssetById(assetId);

        verify(assetRegistrationRepository, times(1)).deleteById(assetId); // Verify deleteById instead of delete
    }


    @Test
    void testDeleteAssetById_NotFound() {
        Long assetId = 1L;
        when(assetRegistrationRepository.findById(assetId)).thenReturn(Optional.empty());

        assertThrows(AssetNotFoundException.class, () -> assetRegistrationService.deleteAssetById(assetId));
    }

}
