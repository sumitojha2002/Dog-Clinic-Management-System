import { Navigate, Outlet } from "react-router-dom";
import SideMenuBar from "../SideMenuBar";
import { useAuth } from "../providers/AuthProvider";

function MainLayout() {
  const { isAuthenticated, role, loading } = useAuth();
  console.log(role);
  if (loading) {
    return <div>Checking authentication....</div>;
  }

  if (!isAuthenticated && role != "ROLE_RECEP") {
    return <Navigate to="/login" replace />;
  }

  return (
    <div>
      <div className="flex border min-h-screen">
        <div className="w-1/5 border md:block sticky top-0 z-50 h-screen self-start">
          <SideMenuBar />
        </div>
        <div className="w-full md:w-4/5 border">
          <Outlet />
        </div>
      </div>
    </div>
  );
}

export default MainLayout;
