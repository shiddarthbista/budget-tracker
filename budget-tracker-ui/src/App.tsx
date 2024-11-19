// App.tsx
import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import AppRoutes from '../src/routes/routes'; // Import the routes.tsx file

const App: React.FC = () => {
  return (
    <Router>
      <div className="App">
        <AppRoutes /> {/* Use the routes defined in routes.tsx */}
      </div>
    </Router>
  );
};

export default App;
