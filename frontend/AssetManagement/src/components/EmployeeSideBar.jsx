import { Link, useNavigate } from "react-router-dom";

import "./sidebar.css"; 

const EmployeeSidebar = () => {

    const navigate = useNavigate();

  const handleSignOut = () => {
    localStorage.removeItem("user");
    window.location.href = "/";
  };
  return (
    <aside className="sidebar">
      <div className="sidebar-header">Employee Dashboard</div>
      <ul className="sidebar-menu">
      <li>
          <Link to="/employee-dashboard">Dashboard</Link>
        </li>
        <li>
          <Link to="/employee-asset-allocation">View Allocated Asset</Link>
        </li>
        <li>
          <Link to="/employee-view-raisedtickets">View All Tickets</Link>
        </li>
        <li>
        <button className="sidebar-signout" onClick={handleSignOut}
        style={{ display: "block", width: "100%", textAlign: "center", padding: "10px", background: "blue", border: "none", color: "white", fontSize: "16px", cursor: "pointer" }}
        >Sign Out</button>
        </li>
        

        
      </ul>
    </aside>
  );
};

export default EmployeeSidebar;

