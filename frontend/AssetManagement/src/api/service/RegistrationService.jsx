import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;


const RegistrationService = {
  
  //add asset
  createAsset: async (formData) => {
    return axios.post(`${API_BASE_URL}/api/registration/register`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

//getall asset 
  getAllAssets: async () => {
  console.log("Fetching assets from:", `${API_BASE_URL}/api/registration/all`); 
  try {
    const response = await axios.get(`${API_BASE_URL}/api/registration/all`);
    console.log("API Response:", response.data); 
    return response;
  } catch (error) {
    console.error("Error fetching assets:", error.response ? error.response.data : error.message); 
    throw error;
  }
},

//get asset by id
getAssetById: async (id) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/api/registration/get/${id}`);
    console.log(response);
    return response;
  } catch (error) {
    console.error(`Error fetching asset with ID ${id}:`, error);
    throw error;
  }
},

//delete asset  by id
deleteAssetById: async (id) => {
  try {
    const response = await axios.delete(`${API_BASE_URL}/api/registration/delete/${id}`);
    console.log(`Asset with ID ${id} deleted successfully.`);
    return response;
  } catch (error) {
    console.error(`Error deleting asset with ID ${id}:`, error);
    throw error;
  }
},

//update asset id 
  updateAsset: async (id, assetData) => {
    console.log("Editing Asset ID:", id);
    const formData = new FormData();
    Object.keys(assetData).forEach((key) => {
      formData.append(key, assetData[key]);
    });

    return axios.put(`${API_BASE_URL}/api/registration/update/${id}`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

};

export default RegistrationService;
