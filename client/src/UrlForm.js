import React, { useState } from 'react';
import { Link } from 'react-router-dom';

export const  UrlForm = () => {
  const [originalUrl, setOriginalUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  

  const handleSubmit = async (e) => {
    e.preventDefault();

  

    try {
     
      // call backend API to generate short URL and retrieve it
      const response = await fetch('http://localhost:8080/api/urls', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ originalUrl }),
      });
      
      if (response.status === 500) {
        const errorData = await response.json();
        window.alert(errorData.message);
        throw new Error(errorData.message);
      }

      const data = await response.json();
      setShortUrl(`http://localhost:8080/api/${data.shortUrl}`);
      setErrorMessage('');
   
    } catch (error) {
      setShortUrl(null);
      setErrorMessage('Error generating short URL');
    }
  };

  const handleInputChange = (e) => {
    setOriginalUrl(e.target.value);
  };
 

  return (
    <div>
    <form onSubmit={handleSubmit}>
      <label htmlFor="originalUrl">Enter the URL to shorten: </label>
      <input
        type="url"
        id="originalUrl"
        name="originalUrl"
        placeholder="https://www.example.com"
        

        value={originalUrl}
        onChange={handleInputChange}
        required
      />
      <button type="submit">Shorten</button>
      {shortUrl && (
        <p>
          Short URL: <a href={shortUrl}>{shortUrl}</a>
        </p>
      )}
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
    </form>
    <div style={{ padding: '10px', margin: '100px 0 0 10px' }} >
    <Link  to ="/redirect">Navigate to Url naviagation Page</Link>
    </div>
    
    </div>
  );
}

