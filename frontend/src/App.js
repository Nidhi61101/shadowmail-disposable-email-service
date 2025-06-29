import React, { useState } from "react";
import Header from "./components/Header";
import EmailList from "./components/EmailList";
import "./App.css";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";


function App() {
  const [currentAddress, setCurrentAddress] = useState("");
  const [emails, setEmails] = useState([]);
  const [selectedEmail, setSelectedEmail] = useState(null);

  return (
    <div className="app-container">
      <ToastContainer position="top-right" autoClose={4000} />
      <Header
        currentAddress={currentAddress}
        setCurrentAddress={setCurrentAddress}
        setEmails={setEmails}
      />

      {currentAddress && (
        <>
          <EmailList
            currentAddress={currentAddress}
            emails={emails}
            setEmails={setEmails}
            setSelectedEmail={setSelectedEmail}
          />
        </>
      )}
    </div>
  );
}

export default App;
