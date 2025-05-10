import axios from 'axios';

const API_URL = 'http://192.168.1.17:8080/api/asset-allocation';

export const getAllAllocations = async () => {
    const response = await axios.get(`${API_URL}/all`);
    console.log("allocation data response",response);
    return response.data;
};

export const getAllocationHistory = async (assetId) => {
    const response = await axios.get(`${API_URL}/history/${assetId}`);
    return response.data;
};

export const returnAsset = async (allocationId, returnedDate) => {
    const response = await axios.post(`${API_URL}/return`, {
        allocationId,
        returnedDate,
    });
    return response.data;
};

export const getAllAssignedAssets = async () => {
    const response = await axios.get(`${API_URL}/assigned-assets`);
    return response.data;
};

export const getAssignedAssetsByEmployee = async (employeeId) => {
    const response = await axios.get(`${API_URL}/assigned-assets/${employeeId}`);
    return response.data;
};

export const updateAssetAllocation = async (allocationId, updates) => {
    const response = await axios.put(`${API_URL}/update/${allocationId}`, updates);
    return response.data;
};

export const allocateAsset = async (assetId, employeeId, userId) => {
    const response = await axios.post(`${API_URL}/allocate`, {
        assetId,
        employeeId,
        userId,
    });
    return response.data;
};

export const allocateAssetByBarcode = async (barcode, employeeId, userId) => {
    console.log("here i service ",barcode,employeeId,userId);
    try {
        const response = await axios.post(`${API_URL}/allocate-by-barcode`, {
            barcode,
            employeeId,
            userId,
        });
        console.log("✅ Allocation successful:", response.data);
        return response.data;
    } catch (error) {
        console.error("❌ Allocation failed:", error.response?.data || error.message);
        throw error;
    }
};
