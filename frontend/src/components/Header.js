
import "../Header.css";
import { toast } from "react-toastify";
import { useEffect  } from "react";

function Header({ currentAddress, setCurrentAddress, setEmails}) {
  // Copy email to clipboard
  const handleCopy = () => {
    if (currentAddress) {
      navigator.clipboard.writeText(currentAddress);
      alert("Copied to clipboard!");
    }
  };

  const handleGenerate = async () => {
    try {
      const response = await fetch("http://localhost:8080/shadowmail/email/create", {
        method: "POST",
      });
      const data = await response.json();
      console.log("Email address",data)
      setCurrentAddress(data.EmailAddress);
      setEmails([]);
    } catch (error) {
      console.error("Error generating email:", error);
      alert("Failed to generate email.");
    }
  };

  useEffect(() => {
    if (
      currentAddress &&
      !currentAddress.startsWith(" ") &&
      !currentAddress.endsWith(" ")
    ) {
      const timeout = setTimeout(() => {
        fetch(
          `http://localhost:8080/shadowmail/email/getExpiry?emailAddress=${currentAddress}`
        )
          .then(async (res) => {
            if (!res.ok) {
              const text = await res.json();
              console.error("Error fetching expiry:", text);
              toast.error("Invalid or expired email address.");
              return;
            }
            const data = await res.json();
            toast.info(`Expires at: ${data.ExpiresAt}`);
          })
          .catch((error) => {
            console.error("Error fetching expiry:", error);
            toast.error("Error fetching expiry.");
          });
      }, 500);

      return () => clearTimeout(timeout);
    }
  }, [currentAddress]);

  return (
    <div className="header-container">
      <h1>📧 ShadowMail</h1>
      <div className="email-info">
        <input
          type="text"
          placeholder="Enter or generate email..."
          value={currentAddress}
          onChange={(e) => setCurrentAddress(e.target.value)}
          className="email-input"
        />
         
        <button className="btn" onClick={handleCopy} disabled={!currentAddress}>
          Copy
        </button>
        <button className="btn" onClick={handleGenerate}>
          Generate New
        </button>
      </div>
    </div>
  );
}

export default Header;
