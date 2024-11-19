import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AccountList from '../components/accounts/AccountList';
import CreateAccount from '../components/accounts/CreateAccount';
import FetchAccount from '../components/accounts/FetchAccount';

function App() {
  return (
    
      <Routes>
        <Route path="/" element={<AccountList />} />
        <Route path="/create" element={<CreateAccount />} />
        <Route path="/fetch-account" element={<FetchAccount />} />
      </Routes>

  );
}

export default App;
