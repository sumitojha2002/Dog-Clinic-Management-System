import Navbar from "../../Navbar";

import { useAuth } from "../../provider/AuthProvider";
import { Navigate, Outlet } from "react-router-dom";

interface Props {
  allowedRole?: string;
}

function ProtectedRouteNavbarNoSwitch({ allowedRole }: Props) {
  const { isAuthenticated, role, loading } = useAuth();

  if (loading) {
    return <div>Checking authentication...</div>;
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (allowedRole && role && !allowedRole.match(role)) {
    return <Navigate to="/unauthorized" replace />;
  }

  return (
    <div>
      <Navbar />

      <div>
        <Outlet />
      </div>
    </div>
  );
}

export default ProtectedRouteNavbarNoSwitch;
