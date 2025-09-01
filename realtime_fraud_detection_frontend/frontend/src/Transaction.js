// import React, { useEffect, useState } from "react";
// import { Client } from "@stomp/stompjs";
// import SockJS from "sockjs-client";

// function Transaction() {
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

// export default Transaction;



import React, { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import "./Transaction.css";   // <-- Import CSS file

function Transaction() {
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    const client = new Client({
      brokerURL: "ws://localhost:8080/ws",
      webSocketFactory: () => new SockJS("http://localhost:8080/ws"),
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
      onConnect: () => {
        console.log("Connected to WebSocket");
        client.subscribe("/topic/fraud", (message) => {
          if (message.body) {
            const parsed = JSON.parse(message.body);
            const { embedding, ...transaction } = parsed;
            setMessages((prev) => [...prev, transaction]);
          }
        });
      },
    });

    client.activate();
    return () => client.deactivate();
  }, []);

  return (
    <div className="transaction-container">
      <h1 className="title">💳 Live Transactions</h1>
      <table className="transaction-table">
        <thead>
          <tr>
            <th>Transaction ID</th>
            <th>User ID</th>
            <th>Amount</th>
            <th>Currency</th>
            <th>Merchant</th>
            <th>Category</th>
            <th>Is Fraud?</th>
            <th>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          {messages.map((msg, i) => (
            <tr key={i}>
              <td>{msg.transactionId}</td>
              <td>{msg.userId}</td>
              <td>{msg.amount.toFixed(2)}</td>
              <td>{msg.currency}</td>
              <td>{msg.merchant}</td>
              <td>{msg.category}</td>
              <td className={msg.isFraud ? "fraud" : "safe"}>
                {msg.isFraud ? "Yes 🚨" : "No ✅"}
              </td>
              <td>{new Date(msg.timestamp).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Transaction;
