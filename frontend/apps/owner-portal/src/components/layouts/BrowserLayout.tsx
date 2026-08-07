import { Outlet } from "react-router-dom";
import Navbar from "../Navbar";
import FilterSideBar from "../browser/FilterSideBar";

export default function BrowserLayout() {
  return (
    <div>
      <div>
        <Navbar />
        <div className="flex gap-5 mt-10">
          <FilterSideBar />
          <Outlet />
        </div>
      </div>
    </div>
  );
}
