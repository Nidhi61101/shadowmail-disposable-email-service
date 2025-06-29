import React, { useEffect } from "react";
import "../EmailList.css";

function EmailList({ currentAddress, emails, setEmails, setSelectedEmail }) {
  useEffect(() => {
    if (currentAddress) {
      fetch(`http://localhost:8080/shadowmail/email/inbox?emailAddress=${currentAddress}`)
        .then(async (res) => {
          if (!res.ok) {
            const text = await res.text();
            console.error("Error response:", text);
            setEmails([]); // Clear inbox
            return;
          }
          const data = await res.json();
          setEmails(data);
        })
        .catch((err) => {
          console.error("Network error fetching inbox:", err);
          setEmails([]); // Clear inbox
        });
    }
  }, [currentAddress, setEmails]);

  if (!currentAddress) {
    return <div className="email-list-container">Please generate an email address.</div>;
  }

  return (
    <div className="email-list-container">
      <h2>Inbox for: {currentAddress}</h2>
      {emails.length === 0 ? (
        <p>No emails found or there was an error fetching the inbox.</p>
      ) : (
        <ul className="email-list">
          {emails.map((email, index) => (
            <li
              key={index}
              className="email-item"
              onClick={() => setSelectedEmail(email)}
            >
              <div className="email-subject">{email.subject}</div>
              <div className="email-meta">
                <span>Status: {email.status}</span>
                <span>{email.receivedAt}</span>
                <span>Body: {email.content}</span>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default EmailList;
