import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL; // Backend URL from .env

const MaintenanceService = {
  // Add Maintenance Log
  addMaintenanceLog: async (adminId, issueDescription, issueImage) => {
    const formData = new FormData();
    formData.append("adminId", adminId);
    formData.append("issueDescription", issueDescription);
    if (issueImage) {
      formData.append("issueImage", issueImage);
    }

    return axios.post(`${API_BASE_URL}/maintenanceLog/logs`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },

  // Get All Maintenance Logs
  getAllLogs: async () => {
    return axios.get(`${API_BASE_URL}/maintenanceLog/all`);
  },

  // Resolve Maintenance Log
  resolveLog: async (logId, invoiceFile) => {
    const formData = new FormData();
    if (invoiceFile) {
      formData.append("invoiceFile", invoiceFile);
    }

    return axios.put(`${API_BASE_URL}/maintenanceLog/${logId}/resolve`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });
  },
};

export default MaintenanceService;
