// import React, { useEffect, useState } from "react";
// import { Client } from "@stomp/stompjs";
// import SockJS from "sockjs-client";

// function App() {
//   const [messages, setMessages] = useState([]);

//   useEffect(() => {
//     // Create STOMP client
//     const client = new Client({
//       brokerURL: "ws://localhost:8080/ws", // If direct WebSocket
//       webSocketFactory: () => new SockJS("http://localhost:8080/ws"), // SockJS fallback
//       reconnectDelay: 5000,
//       debug: (str) => console.log(str),
//       onConnect: () => {
//         console.log("Connected to WebSocket");
//         client.subscribe("/topic/fraud", (message) => {
//           if (message.body) {
//             const parsed = JSON.parse(message.body);
//             // remove embedding to avoid heavy data in state
//             const { embedding, ...transaction } = parsed;
//             setMessages((prev) => [...prev, transaction]);
//           }
//         });
//       },
//     });

//     client.activate();

//     return () => {
//       client.deactivate();
//     };
//   }, []);

//   return (
//     <div style={{ padding: "20px" }}>
//       <h1>Live Transactions</h1>
//       <table border="1" cellPadding="8" cellSpacing="0" style={{ width: "100%", borderCollapse: "collapse" }}>
//         <thead>
//           <tr>
//             <th>Transaction ID</th>
//             <th>User ID</th>
//             <th>Amount</th>
//             <th>Currency</th>
//             <th>Merchant</th>
//             <th>Category</th>
//             <th>Is Fraud?</th>
//             <th>Timestamp</th>
//           </tr>
//         </thead>
//         <tbody>
//           {messages.map((msg, i) => ( 
//             <tr key={i}>
//               <td>{msg.transactionId}</td>
//               <td>{msg.userId}</td>
//               <td>{msg.amount.toFixed(2)}</td>
//               <td>{msg.currency}</td>
//               <td>{msg.merchant}</td>
//               <td>{msg.category}</td>
//               <td style={{ color: msg.isFraud ? "red" : "green", fontWeight: "bold" }}>
//                 {msg.isFraud ? "Yes" : "No"}
//               </td>
//               <td>{new Date(msg.timestamp).toString()}</td>
//             </tr>
//           ))}
//         </tbody>
//       </table>
//     </div>
//   );
// }

// export default App;




// import React from "react";
// import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
// import Signup from "./Signup";
// import Login from "./Login";
// // import Dashboard from "./Dashboard";

// function App() {
//   return (
//     <Router>
//       <div>
//         {/* Navigation links for testing */}
//         <nav>
//           <Link to="/signup">Signup</Link> | 
//           <Link to="/login">Login</Link> 
//           {/* | <Link to="/dashboard">Dashboard</Link> */}
//         </nav>

//         <Routes>
//           <Route path="/signup" element={<Signup />} />
//           <Route path="/login" element={<Login />} />
//           {/* <Route path="/dashboard" element={<Dashboard />} /> */}
//         </Routes>
//       </div>
//     </Router>
//   );
// }

// export default App;
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate, BrowserRouter } from "react-router-dom";
import Signup from "./Signup";
import Login from "./Login";
import Transaction from "./Transaction";
// import AdminPage from "./AdminPage";

function App() {
  const [role, setRole] = useState(null);

  useEffect(() => {
    const storedRole = localStorage.getItem("role");
    if (storedRole) {
      setRole(storedRole);
    }
  }, []);

  const handleLogin = (token, role) => {
    localStorage.setItem("token", token);
    localStorage.setItem("role", role);
    setRole(role);
  };

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/signup" element={<Signup />} />
        <Route path="/login" element={<Login onLogin={handleLogin} />} />
        
        {/* Protected routes */}
        {role === "USER" && (
          <Route path="/transaction" element={<Transaction />} />
        )}
        {role === "ADMIN" && (
          <>
            <Route path="/transaction" element={<Transaction />} />
            {/* <Route path="/admin" element={<AdminPage />} /> */}
          </>
        )}

        {/* Redirect to login if no role */}
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
