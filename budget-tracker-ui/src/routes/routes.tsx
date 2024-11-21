import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from '../pages/HomePage';
import CreateAccount from '../components/accounts/CreateAccount';
import FetchAccount from '../components/accounts/FetchAccount';

function App() {
  return (
    
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/create" element={<CreateAccount />} />
        <Route path="/fetch-account" element={<FetchAccount />} />
      </Routes>

  );
}

export default App;
