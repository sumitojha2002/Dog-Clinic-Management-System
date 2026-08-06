import Navbar from "../Navbar";
import { Outlet } from "react-router-dom";

function ProductLayout() {
  return (
    <div className="border">
      <div>
        <div>
          <Navbar />
          <div>
            <Outlet />
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductLayout;
