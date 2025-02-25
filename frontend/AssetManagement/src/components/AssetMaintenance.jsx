
import React from 'react'
import { useState, useEffect } from "react";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";
import "../components/AssetMaintenance.css";


const AssetMaintenance = () => {
  const [maintenanceLogs, setMaintenanceLogs] = useState([
    { id: 1, assetId: "A001", issueDescription: "Screen not working", createdAt: "2024-02-25", status: "Pending",invoice: null },
    { id: 2, assetId: "A002", issueDescription: "Battery issue", createdAt: "2024-02-24", status: "Pending" ,invoice: null}
  ]);
  const [raisedTickets, setRaisedTickets] = useState([
    { id: 3, assetId: "A003", issueDescription: "Keyboard issue", createdAt: "2024-02-23", status: "Pending",invoice: null },
    { id: 4, assetId: "A004", issueDescription: "Mouse not working", createdAt: "2024-02-22", status: "Pending",invoice: null }
  ]);
  const [showRaisedTickets, setShowRaisedTickets] = useState(false);
  const [log, setLog] = useState({ assetId: "", issueDescription: "", issueImage: null });
  const [showLogForm, setShowLogForm] = useState(false);

  const handleAddLog = (e) => {
    e.preventDefault();
    const newLog = {
      id: maintenanceLogs.length + 1,
      assetId: log.assetId,
      issueDescription: log.issueDescription,
      createdAt: new Date().toISOString().split("T")[0]
    };
    setMaintenanceLogs([...maintenanceLogs, newLog]);
    setLog({ assetId: "", issueDescription: "", issueImage: null });
  };

  const handleTicketStatusChange = (id, status) => {
    setRaisedTickets(raisedTickets.map(ticket => ticket.id === id ? { ...ticket, status } : ticket));
  };
  
  const handleMaintenanceStatusChange = (id, status) => {
    setMaintenanceLogs(maintenanceLogs.map(log => log.id === id ? { ...log, status } : log));
    setRaisedTickets(raisedTickets.map(ticket => ticket.id === id ? { ...ticket, status } : ticket));
  };

  const handleInvoiceUpload = (id, file) => {
    setTickets(tickets.map(ticket => ticket.id === id ? { ...ticket, invoice: file } : ticket));
    setMaintenanceLogs(maintenanceLogs.map(log => log.id === id ? { ...log, invoice: file } : log));
  };



  return (
    <div className="asset-allocation-container">
      <h2>Maintenance Logs</h2>

      <div className="card mb-4">
        <div className="card-header" onClick={() => setShowLogForm(!showLogForm)} style={{ cursor: 'pointer' }}>
          Add Maintenance Log {showLogForm ? '▲' : '▼'}
        </div>
        {showLogForm && (
          <div className="card-body">
            <form onSubmit={handleAddLog}>
              <label className="form-label">Asset ID</label>
              <input type="text" placeholder="Asset ID" value={log.assetId} onChange={(e) => setLog({ ...log, assetId: e.target.value })} className="form-control" required />
              
              <label className="form-label">Issue Description</label>
              <textarea placeholder="Issue Description" value={log.issueDescription} onChange={(e) => setLog({ ...log, issueDescription: e.target.value })} className="form-control" required></textarea>
              
              <label className="form-label">Upload Issue Image</label>
              <input type="file" onChange={(e) => setLog({ ...log, issueImage: e.target.files[0] })} className="form-control" />
              
              <button type="submit" className="btn btn-primary">Add Log</button>
            </form>
          </div>
        )}
      </div>

      <div className="card">
        <div className="card-header">All Maintenance Logs</div>
        <div className="card-body">
          <table className="table table-striped maintenance-table">
            <thead>
              <tr>
                <th>Log ID</th>
                <th>Asset ID</th>
                <th>Issue Description</th>
                <th>Created At</th>
                <th>Status</th>
                <th>Actions</th>
                <th>Invoice</th>
              </tr>
            </thead>
            <tbody>
              {maintenanceLogs.map((log) => (
                <tr key={log.id}>
                  <td>{log.id}</td>
                  <td>{log.assetId}</td>
                  <td>{log.issueDescription}</td>
                  <td>{log.createdAt}</td>
                  <td>{log.status}</td>
                  <td>
                      <input type="file" disabled={log.status !== "Resolved"} onChange={(e) => handleInvoiceUpload(log.id, e.target.files[0])} />
                  </td>
                  <td>
                    <button className="btn btn-warning btn-action" onClick={() => handleMaintenanceStatusChange(log.id, "In Progress")}>In Progress</button>
                    <button className="btn btn-success btn-action" onClick={() => handleMaintenanceStatusChange(log.id, "Resolved")}>Resolved</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      <button className="btn btn-secondary mt-3" onClick={() => setShowRaisedTickets(!showRaisedTickets)}>
        {showRaisedTickets ? "Hide Raised Tickets" : "Load Raised Tickets"}
      </button>

      {showRaisedTickets && (
        <div className="card mt-3">
          <div className="card-header">All Raised Tickets</div>
          <div className="card-body">
            <table className="table table-striped maintenance-table">
              <thead>
                <tr>
                  <th>Log ID</th>
                  <th>Asset ID</th>
                  <th>Issue Description</th>
                  <th>Created At</th>
                  <th>Status</th>
                  <th>Invoice</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {raisedTickets.map((ticket) => (
                  <tr key={ticket.id}>
                    <td>{ticket.id}</td>
                    <td>{ticket.assetId}</td>
                    <td>{ticket.issueDescription}</td>
                    <td>{ticket.createdAt}</td>
                    <td>{ticket.status}</td>
                    <td>
                      <input type="file" disabled={ticket.status !== "Resolved"} onChange={(e) => handleInvoiceUpload(ticket.id, e.target.files[0])} />
                    </td>
                    <td>
                      <button className="btn btn-warning btn-action" onClick={() => handleTicketStatusChange(ticket.id, "In Progress")}>In Progress</button>
                      <button className="btn btn-success btn-action" onClick={() => handleTicketStatusChange(ticket.id, "Resolved")}>Resolved</button>
                    </td>
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
