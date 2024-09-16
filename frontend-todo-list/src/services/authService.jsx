import axios from "axios";

export const login = async (username, password) => {
  try {
    const response = await axios.post("http://localhost:2004/api/v1/auth/access", {
      username: username,
      password: password,
      platform: "miniApp",
    });

    const data = response.data;
    localStorage.setItem('token', data.data.accessToken);

    return data;
  } catch (error) {
    console.error("Login error:", error);
    throw error;
  }
};

export const isValidToken = async (token) => {
  try {
    const response = await axios.get("http://localhost:2004/api/v1/user/profile", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log(response);
    
  } catch (error) {
    // console.error("Token validation error:", error);
  }
};
