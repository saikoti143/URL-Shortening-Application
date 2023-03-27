import axios from "axios";
import React, { useState } from "react";
import { Link } from "react-router-dom";

export const UrlRedirectForm = () => {
  const [shortUrl, setShortUrl] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setError(null);
      const response = await axios.get(shortUrl);
    } catch (error) {
      console.log(error.message);
      if (error.message === "Network Error") {
        window.open(shortUrl, "_blank");
      } else {
        window.alert(error.response.data.message);
        
      }
    }
  };

  const handleInputChange = (e) => {
    setShortUrl(e.target.value);
  };

  return (
    <div>
      <h1> URL Redirect Form </h1>
      <form onSubmit={handleSubmit}>
        <label>Enter short URL:</label>
        <input
          type="url"
          id="shortUrl"
          name="shortUrl"
          placeholder="https://domain/path"
          value={shortUrl}
          onChange={handleInputChange}
        />
        <button style={{margin: '10px'}} type="submit">Navigate</button>
        {error && (
          <div>
            <p style={{ color: "red" }}>{error}</p>
          </div>
        )}
      </form>
      <div style={{ padding: '10px', margin: '100px 0 0 10px' }}>
        <Link to="/">Home</Link>
      </div>
    </div>
  );
};
