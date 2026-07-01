import { Outlet } from "react-router-dom";
import Navbar from "../Navbar";

function MainLayout() {
  return (
    <div>
      <div>
        <div>
          <Navbar />
        </div>
        <Outlet />
      </div>
    </div>
  );
}

export default MainLayout;
