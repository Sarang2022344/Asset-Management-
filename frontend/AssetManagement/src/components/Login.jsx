import React, { useState } from "react";
import "./login.css"; 

const Login = ({ onLogin }) => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
      onLogin(); 
   
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Login</h2>

        <div className="input-group">
          <label>Username</label>
          <input
            type="text"
            name="username"
            
          
            placeholder="Enter your username"
          
          />
        </div>

        <div className="input-group">
          <label>Password</label>
          <input
            type="password"
            name="password"
            
         
            placeholder="Enter your password"
            
          />
        </div>

        <button type="submit" className="login-btn">Login</button>
      </form>
    </div>
  );
};

export default Login;
