import { Outlet, Navigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import { _Navbar } from "./_Navbar";

const ProtectedLayout = () => {
  const { user } = useAuth();

  return user ? (
    <>
      <_Navbar /> <Outlet />
    </>
  ) : (
    <Navigate to="/login" />
  );
};

export default ProtectedLayout;
