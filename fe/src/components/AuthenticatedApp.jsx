import { useEffect, useState } from "react";
import { getUser } from "../service/authService";
import { _Navbar } from "./layout/_Navbar";
import { logout } from "../service/authService";
import {Todo} from "./todo/Todo"
export const AuthenticatedApp = () => {
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await getUser();
        setProfile(result);
      } catch (err) {
        setError(err.message || "An error occurred");
      }
    };
    fetchData();
  }, []);

  const onLogout = async () => {
    await logout();
  };
  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <_Navbar
        username={profile && profile.data.username}
        handleLogout={onLogout}
      />
      <div className="container">
        <h1>Authenticated </h1>
        <Todo/>
        {profile && <pre>{JSON.stringify(profile, null, 2)}</pre>}
      </div>
    </div>
  );
};
