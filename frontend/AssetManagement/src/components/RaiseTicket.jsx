
import React from 'react'
import { useState, useEffect } from "react";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";

const RaiseTicket = () =>{
      const [allocatedAssets, setAllocatedAssets] = useState([
        { id: 1, assetName: "Laptop", status: "Assigned" },
        { id: 2, assetName: "Monitor", status: "Assigned" }
      ]);
      const [showRaiseTicketForm, setShowRaiseTicketForm] = useState(false);
      const [selectedAsset, setSelectedAsset] = useState(null);
      const [ticket, setTicket] = useState({ assetName: "", issueDescription: "", issueImage: null });
    
      const handleOpenTicketForm = (asset) => {
        setSelectedAsset(asset);
        setTicket({ assetName: asset.assetName, issueDescription: "", issueImage: null });
        setShowRaiseTicketForm(true);
      };
    
      const handleAddTicket = (e) => {
        e.preventDefault();
        setShowRaiseTicketForm(false);
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
                    <tr key={asset.id}>
                      <td>{asset.assetName}</td>
                      <td>{asset.status}</td>
                      <td>
                        <button className="btn btn-primary btn-action" onClick={() => handleOpenTicketForm(asset)}>Raise Ticket</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
    
          {showRaiseTicketForm && (
            <div className="card mt-3">
              <div className="card-header">Raise a Ticket for {selectedAsset?.assetName}</div>
              <div className="card-body">
                <form onSubmit={handleAddTicket}>
                  <label className="form-label">Issue Description</label>
                  <textarea placeholder="Describe the issue" value={ticket.issueDescription} onChange={(e) => setTicket({ ...ticket, issueDescription: e.target.value })} className="form-control" required></textarea>
                  
                  <label className="form-label">Upload Issue Image</label>
                  <input type="file" onChange={(e) => setTicket({ ...ticket, issueImage: e.target.files[0] })} className="form-control" />
                  
                  <button type="submit" className="btn btn-success mt-2">Submit Ticket</button>
                  <button type="button" className="btn btn-secondary mt-2 ms-2" onClick={() => setShowRaiseTicketForm(false)}>Cancel</button>
                </form>
              </div>
            </div>
          )}
        </div>

        );
  
};

  export default RaiseTicket;
  