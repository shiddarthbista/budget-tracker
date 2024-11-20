import React, { useState } from 'react';
import axios from 'axios';

const CreateAccount: React.FC = () => {
  const [formData, setFormData] = useState({
    accountHolderName: '',
    email: '',
    accountBalance: 0,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    axios.post('/api/accounts/createNewAccount', formData)
      .then(response => alert(response.data))
      .catch(error => console.error(error));
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h1>Create Account</h1>
      <input
        type="text"
        name="accountHolderName"
        placeholder="Name"
        value={formData.accountHolderName}
        onChange={handleChange}
      />
      <input
        type="email"
        name="email"
        placeholder="Email"
        value={formData.email}
        onChange={handleChange}
      />
      <input
        type="number"
        name="accountBalance"
        placeholder="Balance"
        value={formData.accountBalance}
        onChange={handleChange}
      />
      <button type="submit">Create</button>
    </form>
  );
};

export default CreateAccount;
