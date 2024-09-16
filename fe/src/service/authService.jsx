import axios from "axios";

const API = "http://localhost:2004/api/v1/";

export const login = async (username, password, platform) => {
  try {
    const data = {
      username,
      password,
      platform: "web",
    };
    const res = await axios
      .post(`${API}auth/access`, data)
      .then((res) => res.data);
    console.log(res.data);
    localStorage.setItem("authentication", res.data.accessToken);
    return res.data;
  } catch (error) {
    console.error(error.response.data);
    return error.response.data;
  }
};

export const logout = async () => {
  try {
    const token = localStorage.getItem("authentication");
    await axios.post(`${API}auth/remove`, null,{
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    localStorage.removeItem("authentication");
    window.location.reload();
  } catch (error) {
    console.error(error);
  }
};

export const getUser = async () => {
  try {
    const token = localStorage.getItem("authentication");
    const res = await axios
      .get(`${API}user/profile`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => res.data);
    return res;
  } catch (error) {
    log.error("Error ", error);
  }
};
