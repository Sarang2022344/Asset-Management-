import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const CategoryService = {
  getAllCategories: async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/api/categories`);
      return response.data; 
    } catch (error) {
      console.error("Error fetching categories:", error);
      throw error;
    }
  },
};

export default CategoryService;
