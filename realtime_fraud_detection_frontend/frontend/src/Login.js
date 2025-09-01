// import React, { useState } from "react";
// import { useNavigate } from "react-router-dom";  // ✅ import navigate hook

// function Login({ onLogin }) {
//   const [username, setUsername] = useState("");
//   const [password, setPassword] = useState("");
//     const navigate = useNavigate();  // ✅ initialize navigate


//   const handleLogin = async () => {
//     const response = await fetch("http://localhost:8080/auth/login", {
//       method: "POST",
//       headers: { "Content-Type": "application/json" },
//       body: JSON.stringify({ username, password }),
//     });

//     if (response.ok) {
//       const data = await response.json(); // backend should return { token, role }
//       onLogin(data.token, data.role);

//     localStorage.setItem("token", data.token);
//     localStorage.setItem("role", data.role);
//     // setRole(role);

//       if (data.role === "admin") {
//         navigate("/transaction");
//       } else {
//         navigate("/transaction");  // ✅ correct route
//       }
//     } else {
//       alert("Login failed!");
//     }
//   };

//   return (
//     <div>
//       <h2>Login</h2>
//       <input placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
//       <input placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
//       <button onClick={handleLogin}>Login</button>
//     </div>
//   );
// }

// export default Login;



import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Auth.css"; // ✅ import same CSS as Signup

function Login({ onLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
      const data = await response.json(); // backend should return { token, role }
      onLogin(data.token, data.role);

      localStorage.setItem("token", data.token);
      localStorage.setItem("role", data.role);

      navigate("/transaction");
    } else {
      alert("Login failed!");
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>Login</h2>
        <input
          className="auth-input"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          className="auth-input"
          placeholder="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className="auth-button" onClick={handleLogin}>
          Login
        </button>

        <p className="auth-footer">
          Don’t have an account?{" "}
          <span className="auth-link" onClick={() => navigate("/signup")}>
            Sign up
          </span>
        </p>
      </div>
    </div>
  );
}

export default Login;
