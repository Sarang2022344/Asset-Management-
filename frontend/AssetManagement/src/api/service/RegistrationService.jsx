import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;


const RegistrationService = {
  
  createAsset: async (formData) => {
    return axios.post(`${API_BASE_URL}/api/registration/register`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },


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



};

export default RegistrationService;
