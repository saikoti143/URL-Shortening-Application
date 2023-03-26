
import React from 'react';
import {BrowserRouter as Router,Routes, Route,Link} from "react-router-dom";
import { UrlRedirectForm } from './UrlRedirectForm';
import{UrlForm} from'./UrlForm';
import { Home } from './Home';



export const App = () => {
  return (
  
    <div>

    <center style={{margin:200}}>
      <h1>URL Shortening Application</h1>
     
          <Router>
          <Routes>
            <Route exact path='/' element={<UrlForm/>}/>
            <Route path='/redirect' element={<UrlRedirectForm/>}/>
        
            </Routes> 
              </Router>
              
      
    </center>
    
    
    </div>
  )
}

export default App;

