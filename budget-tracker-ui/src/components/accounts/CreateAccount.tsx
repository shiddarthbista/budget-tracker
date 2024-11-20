import React, { useState } from 'react';
import axios from 'axios';

const CreateAccount: React.FC = () => {
  const [formData, setFormData] = useState({
    accountHolderName: '',
    email: '',
    accountBalance: 0,
  });
  const [errors, setErrors] = useState<{ [key: string]: string }>({});
  const [successMessage, setSuccessMessage] = useState('');

  const validateForm = () => {
    const newErrors: { [key: string]: string } = {};
    if (!formData.accountHolderName) newErrors.accountHolderName = 'Name is required';
    if (!formData.email) {
      newErrors.email = 'Email is required';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = 'Enter a valid email';
    }
    if (formData.accountBalance <= 0) {
      newErrors.accountBalance = 'Balance must be greater than 0';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    axios
      .post('/api/accounts/createNewAccount', formData)
      .then((response) => {
        setSuccessMessage('Account created successfully!');
        setFormData({ accountHolderName: '', email: '', accountBalance: 0 });
        setErrors({});
      })
      .catch((error) => {
        setErrors({ form: 'Failed to create account. Please try again.' });
        console.error(error);
      });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.heading}>Create Account</h1>
      <form onSubmit={handleSubmit} style={styles.form}>
        <div style={styles.inputGroup}>
          <label htmlFor="accountHolderName" style={styles.label}>
            Account Holder Name:
          </label>
          <input
            type="text"
            name="accountHolderName"
            id="accountHolderName"
            placeholder="Enter your name"
            value={formData.accountHolderName}
            onChange={handleChange}
            style={styles.input}
          />
          {errors.accountHolderName && <span style={styles.error}>{errors.accountHolderName}</span>}
        </div>

        <div style={styles.inputGroup}>
          <label htmlFor="email" style={styles.label}>
            Email:
          </label>
          <input
            type="email"
            name="email"
            id="email"
            placeholder="Enter your email"
            value={formData.email}
            onChange={handleChange}
            style={styles.input}
          />
          {errors.email && <span style={styles.error}>{errors.email}</span>}
        </div>

        <div style={styles.inputGroup}>
          <label htmlFor="accountBalance" style={styles.label}>
            Initial Balance:
          </label>
          <input
            type="number"
            name="accountBalance"
            id="accountBalance"
            placeholder="Enter initial balance"
            value={formData.accountBalance}
            onChange={handleChange}
            style={styles.input}
          />
          {errors.accountBalance && <span style={styles.error}>{errors.accountBalance}</span>}
        </div>

        {errors.form && <p style={styles.error}>{errors.form}</p>}
        {successMessage && <p style={styles.success}>{successMessage}</p>}

        <button type="submit" style={styles.button}>
          Create Account
        </button>
      </form>
    </div>
  );
};

const styles = {
  container: {
    padding: '20px',
    maxWidth: '500px',
    margin: '0 auto',
    backgroundColor: '#f9f9f9',
    borderRadius: '8px',
    boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
  },
  heading: {
    textAlign: 'center' as React.CSSProperties['textAlign'], // Cast explicitly
    marginBottom: '20px',
    color: '#333',
  },
  form: {
    display: 'flex' as React.CSSProperties['display'], // Explicit cast for display
    flexDirection: 'column' as React.CSSProperties['flexDirection'], // Explicit cast for flexDirection
  },
  inputGroup: {
    marginBottom: '15px',
  },
  label: {
    display: 'block',
    marginBottom: '5px',
    color: '#555',
  },
  input: {
    width: '95%',
    padding: '10px',
    border: '1px solid #ccc',
    borderRadius: '4px',
  },
  button: {
    padding: '10px',
    backgroundColor: '#007BFF',
    color: 'white',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
  error: {
    color: 'red',
    fontSize: '0.875rem',
    marginTop: '5px',
  },
  success: {
    color: 'green',
    fontSize: '0.875rem',
    marginTop: '10px',
  },
};

export default CreateAccount;
