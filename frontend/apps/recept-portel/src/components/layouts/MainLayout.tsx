import React from "react";
import { Outlet } from "react-router-dom";
import SideMenuBar from "../SideMenuBar";

function MainLayout() {
  return (
    <div>
      <div className="flex border min-h-screen">
        <div className="w-1/5 border md:block">
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
