import React, { useState, useEffect } from "react";
import MaintenanceService from "../api/service/MaintenanceService";
import axios from "axios";

const AssetMaintenance = () => {
  const [maintenanceLogs, setMaintenanceLogs] = useState([]);
  const [raisedTickets, setRaisedTickets] = useState([]);
  const [showRaisedTickets, setShowRaisedTickets] = useState(false);
  const [log, setLog] = useState({ assetId: "", issueDescription: "", issueImage: null });
  const [showLogForm, setShowLogForm] = useState(false);
  const employeeId = 2; // Hardcoded Employee ID for now

  useEffect(() => {
    fetchMaintenanceLogs();
    fetchRaisedTickets();
  }, []);

  const fetchMaintenanceLogs = async () => {
    try {
      const response = await axios.get("http://localhost:8080/maintenanceLog/all");
      setMaintenanceLogs(response.data);
    } catch (error) {
      console.error("Error fetching maintenance logs:", error);
    }
  };

  const fetchRaisedTickets = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/tickets/employee/${employeeId}`);
      setRaisedTickets(response.data);
    } catch (error) {
      console.error("Error fetching raised tickets:", error);
    }
  };

  const handleAddLog = async (e) => {
    e.preventDefault();
    try {
      const assetResponse = await axios.get("http://localhost:8080/api/registration/all");
      const unallocatedAssets = assetResponse.data;

      if (!unallocatedAssets.some(asset => asset.id === log.assetId)) {
        alert("This asset is allocated to an employee and cannot be put into maintenance.");
        return;
      }

      await MaintenanceService.addMaintenanceLog(1, log.issueDescription, log.issueImage);
      alert("Maintenance log added successfully!");
      fetchMaintenanceLogs();
      setLog({ assetId: "", issueDescription: "", issueImage: null });
      setShowLogForm(false);
    } catch (error) {
      console.error("Error adding maintenance log:", error);
    }
  };

  const handleMaintenanceStatusChange = async (logId) => {
    try {
      await axios.put(`http://localhost:8080/maintenanceLog/${logId}/resolve`);
      alert("Maintenance status updated!");
      fetchMaintenanceLogs();
    } catch (error) {
      console.error("Error updating maintenance status:", error);
    }
  };

  return (
    <div className="asset-allocation-container">
      <h2>Asset Maintenance</h2>

      {/* Admin: Add Maintenance Log Form */}
      <div className="card mb-4">
        <div className="card-header" onClick={() => setShowLogForm(!showLogForm)} style={{ cursor: "pointer" }}>
          Add Maintenance Log {showLogForm ? "▲" : "▼"}
        </div>
        {showLogForm && (
          <div className="card-body">
            <form onSubmit={handleAddLog}>
              <label className="form-label">Asset ID</label>
              <input
                type="text"
                placeholder="Asset ID"
                value={log.assetId}
                onChange={(e) => setLog({ ...log, assetId: e.target.value })}
                className="form-control"
                required
              />
              <label className="form-label">Issue Description</label>
              <textarea
                placeholder="Describe the issue"
                value={log.issueDescription}
                onChange={(e) => setLog({ ...log, issueDescription: e.target.value })}
                className="form-control"
                required
              ></textarea>
              <label className="form-label">Upload Issue Image</label>
              <input type="file" onChange={(e) => setLog({ ...log, issueImage: e.target.files[0] })} className="form-control" />
              <button type="submit" className="btn btn-primary">Add Log</button>
            </form>
          </div>
        )}
      </div>

      {/* Admin: View All Maintenance Logs */}
      <div className="card">
        <div className="card-header">Assets in Maintenance (Admin View)</div>
        <div className="card-body">
          <table className="table table-striped maintenance-table">
            <thead>
              <tr>
                <th>Log ID</th>
                <th>Asset Name</th>
                <th>Issue Description</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {maintenanceLogs.map((log) => (
                <tr key={log.id}>
                  <td>{log.id}</td>
                  <td>{log.assetName}</td>
                  <td>{log.issueDescription}</td>
                  <td>{log.status}</td>
                  <td>
                    {log.status !== "Resolved" && (
                      <button className="btn btn-success btn-action" onClick={() => handleMaintenanceStatusChange(log.id)}>
                        Resolve
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Employee: View Raised Tickets */}
      <button className="btn btn-secondary mt-3" onClick={() => setShowRaisedTickets(!showRaisedTickets)}>
        {showRaisedTickets ? "Hide Raised Tickets" : "Load Raised Tickets"}
      </button>

      {showRaisedTickets && (
        <div className="card mt-3">
          <div className="card-header">Tickets Raised by Employee</div>
          <div className="card-body">
            <table className="table table-striped maintenance-table">
              <thead>
                <tr>
                  <th>Ticket ID</th>
                  <th>Asset Name</th>
                  <th>Issue Description</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {raisedTickets.map((ticket) => (
                  <tr key={ticket.id}>
                    <td>{ticket.id}</td>
                    <td>{ticket.assetName}</td>
                    <td>{ticket.issueDescription}</td>
                    <td>{ticket.status}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
};

export default AssetMaintenance;
