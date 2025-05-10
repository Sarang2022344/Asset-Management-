import React, { useState, useEffect } from "react";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";

const RaiseTicket = () => {
  const [allocatedAssets, setAllocatedAssets] = useState([]);
  const [showRaiseTicketForm, setShowRaiseTicketForm] = useState(false);
  const [selectedAsset, setSelectedAsset] = useState(null);
  const [ticket, setTicket] = useState({ assetName: "", issueDescription: "", issueImage: null });

  useEffect(() => {
    fetchAllocatedAssets();
  }, []);

  const fetchAllocatedAssets = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/asset-allocation/assigned-assets/2");
      console.log("API Response:", response.data);  // Debugging: Check structure
      setAllocatedAssets(response.data);
    } catch (error) {
      console.error("Error fetching allocated assets:", error);
    }
  };

  const handleOpenTicketForm = (asset) => {
    setSelectedAsset(asset);
    setTicket({ assetName: asset.asset?.name, issueDescription: "", issueImage: null });
    setShowRaiseTicketForm(true);
  };

  const handleAddTicket = async (e) => {
    e.preventDefault();
    try {
      const formData = new FormData();
      formData.append("assetName", ticket.assetName);
      formData.append("issueDescription", ticket.issueDescription);
      if (ticket.issueImage) {
        formData.append("issueImage", ticket.issueImage);
      }
      await axios.post("http://localhost:8080/tickets/employee/2/raise-ticket", formData);
  
      alert("Ticket raised successfully!");
      setShowRaiseTicketForm(false);
    } catch (error) {
      console.error("Error raising ticket:", error);
    }
  };

  return (
    <div className="asset-allocation-container">
      <h2>Assigned Assets</h2>
      <div className="card">
        <div className="card-body">
          <table className="table table-striped maintenance-table">
            <thead>
              <tr>
                <th>Asset Name</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {allocatedAssets.map((asset) => (
                <tr key={asset.allocationId}>
                  <td>{asset.asset?.name}</td> {/* Fix: Correctly access asset name */}
                  <td>{asset.status}</td>
                  <td>
                    <button className="btn btn-primary btn-action" onClick={() => handleOpenTicketForm(asset)}>
                      Raise Ticket
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {showRaiseTicketForm && (
        <div className="card mt-3">
          <div className="card-header">Raise a Ticket for {selectedAsset?.asset?.name}</div>
          <div className="card-body">
            <form onSubmit={handleAddTicket}>
              <label className="form-label">Issue Description</label>
              <textarea
                placeholder="Describe the issue"
                value={ticket.issueDescription}
                onChange={(e) => setTicket({ ...ticket, issueDescription: e.target.value })}
                className="form-control"
                required
              ></textarea>
              <label className="form-label">Upload Issue Image</label>
              <input type="file" onChange={(e) => setTicket({ ...ticket, issueImage: e.target.files[0] })} className="form-control" />
              <button type="submit" className="btn btn-success mt-2">Submit Ticket</button>
              <button type="button" className="btn btn-secondary mt-2 ms-2" onClick={() => setShowRaiseTicketForm(false)}>
                Cancel
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default RaiseTicket;
