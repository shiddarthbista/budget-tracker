import axios from 'axios';

// Base URL for your API
const API_URL = 'http://localhost:8080/api/accounts';

// Fetch an account by its ID
export const getAccountById = async (accountId: string) => {
  try {

   
    const response = await axios.get(`${API_URL}/${accountId}`);
    return response.data; // Return account data
  } catch (error) {
    console.error("Error fetching account:", error);
    throw error;
  }
};

// Create a new account
export const createAccount = async (accountData: any) => {
  try {
    const response = await axios.post(`${API_URL}/createNewAccount`, accountData);
    return response.data; // Return success message or account info
  } catch (error) {
    console.error("Error creating account:", error);
    throw error;
  }
};

// Update an existing account
export const updateAccount = async (accountId: string, accountData: any) => {
  try {
    const response = await axios.put(`${API_URL}/${accountId}`, accountData);
    return response.data; // Return success message
  } catch (error) {
    console.error("Error updating account:", error);
    throw error;
  }
};

// Delete an account by ID
export const deleteAccount = async (accountId: string) => {
  try {
    const response = await axios.delete(`${API_URL}/${accountId}`);
    return response.data; // Return success message
  } catch (error) {
    console.error("Error deleting account:", error);
    throw error;
  }
};
