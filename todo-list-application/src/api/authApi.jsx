import axios from 'axios';

const API_URL = 'http://localhost:2004/api/v1/auth';

export const login = async (username, password) => {
  const response = await axios.post(`${API_URL}/access`, {
    username,
    password,
    platform: 'web'
  }).then(res => res.data);
  return response.data;
};
