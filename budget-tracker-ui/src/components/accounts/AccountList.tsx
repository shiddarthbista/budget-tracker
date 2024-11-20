import React, { useEffect, useState } from 'react';
import axios from 'axios';

type Account = {
  accountId: string;
  accountHolderName: string;
  accountBalance: number;
};

const AccountList: React.FC = () => {
  const [accounts, setAccounts] = useState<Account[]>([]); // Explicitly define the type

  useEffect(() => {
    axios
      .get('/api/accounts')
      .then(response => setAccounts(response.data))
      .catch(error => console.error(error));
  }, []);

  return (
    <div>
      <h1>Account List</h1>
      <ul>
        {accounts.map(account => (
          <li key={account.accountId}>
            {account.accountHolderName} - ${account.accountBalance}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AccountList;
