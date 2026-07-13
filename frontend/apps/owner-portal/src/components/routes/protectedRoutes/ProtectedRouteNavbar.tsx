import Navbar from "../../Navbar";
import ProfileSwitcher from "../../ProfileSwitcher";
import { useAuth } from "../../provider/AuthProvider";
import { Navigate, Outlet, ScrollRestoration } from "react-router-dom";

interface Props {
  allowedRole?: string;
}

function ProtectedRoute({ allowedRole }: Props) {
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
        <ProfileSwitcher />
      </div>
      <div>
        <Outlet />
      </div>
    </div>
  );
}

export default ProtectedRoute;
