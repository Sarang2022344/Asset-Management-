import axios from "axios";

// const API_URL = "http://192.168.1.13:8080/disposal"; 
// const ASSET_API_URL = "http://192.168.1.13:8080/api/registration"; 
const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

class AssetDisposalService {
  
  async addDisposal(disposalData) {
    try {
      const response = await axios.post(`${API_BASE_URL}/disposal`, disposalData);
      console.log("Add Disposal Data : ",disposalData);
  
      return response.data;
    } catch (error) {
      console.error("Error adding disposal:", error);
      throw error;
    }
  }
  
  async getAllDisposals() {
    try {
      const response = await axios.get(`${API_BASE_URL}/disposal`);
      const disposals = response.data;

      // Fetch asset names for each asset ID
      const updatedDisposals = await Promise.all(
        disposals.map(async (disposal) => {
          try {
            const assetResponse = await axios.get(`${API_BASE_URL}/api/registration/get/${disposal.assetId}`);
            const assetData = assetResponse.data;
            console.log("Asset Response:", assetResponse.data);

            return {
              ...disposal,
              assetName: assetData.name || "Unknown", // Ensure assetName exists
            };
          } catch (error) {
            console.error(`Error fetching asset name for ID ${disposal.assetId}:`, error);
            return { ...disposal, assetName: "Unknown" };
          }
        })
      );
      console.log("Updated Disposals with Asset Names:", updatedDisposals);
      return updatedDisposals;
    } catch (error) {
      console.error("Error fetching disposals:", error);
      throw error;
    }
  }

  async updateDisposal(disposalId, updatedData) {
    console.log("disposal id at updatedisposal",disposalId);
    console.log("data from updatedisposal service",updatedData);
    try {
      const response = await axios.put(`${API_BASE_URL}/disposal/${disposalId}`, updatedData);
      return response.data;
    } catch (error) {
      console.error(`Error updating disposal with ID ${disposalId}:`, error);
      throw error;
    }
  }

  async getDisposedAssetsByDateRange(startDate, endDate) {
    try {
      const response = await axios.get(`${API_BASE_URL}/range`, {
        params: {
          startDate,
          endDate,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error fetching filtered data:", error);
      throw error;
    }
  }
}

export default new AssetDisposalService();
