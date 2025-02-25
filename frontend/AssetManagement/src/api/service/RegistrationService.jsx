import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;


const RegistrationService = {
  
  createAsset: async (formData) => {
    return axios.post(`${API_BASE_URL}/register`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

  getAllAssets: async () => {
  console.log("Fetching assets from:", `${API_BASE_URL}/all`); 
  try {
    const response = await axios.get(`${API_BASE_URL}/all`);
    console.log("API Response:", response.data); 
    return response;
  } catch (error) {
    console.error("Error fetching assets:", error.response ? error.response.data : error.message); 
    throw error;
  }
},

};

export default RegistrationService;
