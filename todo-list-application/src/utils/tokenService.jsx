const TOKEN_KEY = 'accessToken';
const REFRESH_TOKEN_KEY = 'refreshToken';
const USER_KEY = 'user';

export const tokenService = {
  saveUser(user) {
    localStorage.setItem(USER_KEY, JSON.stringify(user.userId));
    localStorage.setItem(TOKEN_KEY, user.accessToken);
    localStorage.setItem(REFRESH_TOKEN_KEY, user.refreshToken);
  },

  getUser() {
    const user = localStorage.getItem(USER_KEY);
    return user ? JSON.parse(user) : null;
  },

  clearUser() {
    localStorage.removeItem(USER_KEY);
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(REFRESH_TOKEN_KEY);
  },

  getAccessToken() {
    return localStorage.getItem(TOKEN_KEY);
  }
};
