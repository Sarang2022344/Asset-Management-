import { Link } from "react-router-dom";
import "./sidebar.css"; 

const Sidebar = () => {
  return (
    <aside className="sidebar">
      <div className="sidebar-header">Dashboard</div>
      <ul className="sidebar-menu">
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
      </ul>
    </aside>
  );
};

export default Sidebar;

