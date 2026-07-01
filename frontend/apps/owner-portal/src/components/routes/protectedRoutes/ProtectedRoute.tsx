import { useAuth } from "../../provider/AuthProvider";
import { Navigate } from "react-router-dom";

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

  return <div>{role}</div>;
}

export default ProtectedRoute;
