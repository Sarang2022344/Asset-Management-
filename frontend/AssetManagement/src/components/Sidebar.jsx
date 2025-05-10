
import { Link,useNavigate } from "react-router-dom";
import "./sidebar.css"; 

const Sidebar = () => {
  const navigate = useNavigate();

  const handleSignOut = () => {
    localStorage.removeItem("user");
    window.location.href = "/";
  };
  return (
    <aside className="sidebar">
      <div className="sidebar-header">Dashboard</div>
      <ul className="sidebar-menu">
      <li>
          <Link to="/asset-dashboard">Dashboard</Link>
        </li>
        <li>
          <Link to="/asset-registration">Asset Registration</Link>
        </li>
        <li>
          <Link to="/asset-allocation">Asset Allocation</Link>
        </li>
        <li>
          <Link to="/asset-maintenance">Asset Maintenance</Link>
        </li>
        <li>
          <Link to="/asset-disposal">Asset Disposal</Link>
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

export default Sidebar;

