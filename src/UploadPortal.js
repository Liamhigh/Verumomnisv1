import React, { useState } from "react";

export default function UploadPortal() {
  const [userType, setUserType] = useState("");
  const [file, setFile] = useState(null);

  return (
    <div style={{ padding: 20, fontFamily: "sans-serif", textAlign: "center" }}>
      <h1>Verum Omnis Secure Upload Portal</h1>
      <p>Select your role:</p>
      <select value={userType} onChange={(e) => setUserType(e.target.value)}>
        <option value="">-- Choose --</option>
        <option value="private">Private Individual</option>
        <option value="company">Company</option>
        <option value="institution">Institution</option>
      </select>

      {userType && (
        <>
          <p style={{ marginTop: 20 }}>Upload your document:</p>
          <input type="file" onChange={(e) => setFile(e.target.files[0])} />
          {file && <p>Selected: {file.name}</p>}
        </>
      )}
    </div>
  );
}