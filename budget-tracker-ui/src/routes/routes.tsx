import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AccountList from '../components/accounts/AccountList';
import CreateAccount from '../components/accounts/CreateAccount';

function App() {
  return (
    
      <Routes>
        <Route path="/" element={<AccountList />} />
        <Route path="/create" element={<CreateAccount />} />
      </Routes>

  );
}

export default App;
