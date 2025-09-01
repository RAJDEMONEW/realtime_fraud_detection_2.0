// import React, { useState } from "react";
// import "./Signup.css"; // ✅ import CSS file

// function Signup() {
//   const [username, setUsername] = useState("");
//   const [password, setPassword] = useState("");
//   const [role, setRole] = useState("user"); // default role = user

//   const handleSignup = async () => {
//     const response = await fetch("http://localhost:8080/auth/signup", {
//       method: "POST",
//       headers: { "Content-Type": "application/json" },
//       body: JSON.stringify({ username, password, role }),
//     });

//     if (response.ok) {
//       alert("Signup successful! Please login.");
//       window.location.href = "/login";
//     } else {
//       alert("Signup failed.");
//     }
//   };

//   return (
//     <div>
//       <h2>Signup</h2>
//       <input placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
//       <input placeholder="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
      
//       <select value={role} onChange={(e) => setRole(e.target.value)}>
//         <option value="user">User</option>
//         <option value="admin">Admin</option>
//       </select>

//       <button onClick={handleSignup}>Signup</button>
//     </div>
//   );
// }

// export default Signup;



import React, { useState } from "react";
import "./Signup.css"; // ✅ Import CSS file

function Signup() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("user");

  const handleSignup = async () => {
    const response = await fetch("http://localhost:8080/auth/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password, role }),
    });

    if (response.ok) {
      alert("Signup successful! Please login.");
      window.location.href = "/login";
    } else {
      alert("Signup failed.");
    }
  };

  return (
    <div className="signup-container">
      <div className="signup-card">
        <h2>Create Account</h2>

        <input
          className="signup-input"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          className="signup-input"
          placeholder="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <select
          className="signup-select"
          value={role}
          onChange={(e) => setRole(e.target.value)}
        >
          <option value="user">User</option>
          <option value="admin">Admin</option>
        </select>

        <button className="signup-btn" onClick={handleSignup}>
          Signup
        </button>
      </div>
    </div>
  );
}

export default Signup;
