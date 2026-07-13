import { Outlet } from "react-router-dom";

function AuthLayout() {
  return (
    <div className="flex flex-col justify-center h-screen items-center">
      <Outlet />
    </div>
  );
}

export default AuthLayout;
