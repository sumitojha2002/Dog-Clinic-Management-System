import { useAuth } from "./providers/AuthProvider";
import Logo from "../../src/assets/logo.png";
import {
  BriefcaseMedical,
  ClipboardList,
  Home,
  LogOut,
  PawPrint,
  UserPen,
} from "lucide-react";
import { NavLink } from "react-router-dom";
import { Button } from "./ui/button";

function SideMenuBar() {
  const auth = useAuth();

  return (
    <div className="pl-5 pt-5  pr-5">
      <div className="flex items-center">
        <img src={Logo} alt="" className="w-15" />
        <div className="pl-5">
          <h1 className="text-2xl hidden md:block  md:text-[15px] lg:text-2xl">
            Nirbhay Sunwar
          </h1>
          <p className="text-gray-600 hidden md:block  md:text-[10px] lg:text-[14px]">
            Receptionist
          </p>
        </div>
      </div>
      <div className="pl-4 pt-10 flex gap-10 flex-col  justify-between ">
        <NavLink to="/">
          <div className="flex gap-2">
            <Home />
            <h1 className="hidden md:block">Home</h1>
          </div>
        </NavLink>
        <NavLink to="/appointments">
          <div className="flex gap-2">
            <ClipboardList />
            <h1 className="hidden md:block">Appointment</h1>
          </div>
        </NavLink>
        <NavLink to="/veterinarian">
          <div className="flex gap-2">
            <BriefcaseMedical />
            <h1 className="hidden md:block">Veterinarian</h1>
          </div>
        </NavLink>
        <NavLink to="/dogs">
          <div className="flex gap-2">
            <PawPrint />
            <h1 className="hidden md:block">Dogs</h1>
          </div>
        </NavLink>
        <NavLink to="/profile">
          <div className="flex gap-2">
            <UserPen />
            <h1 className="hidden md:block">Profile</h1>
          </div>
        </NavLink>

        <Button className="w-full" onClick={() => auth.logout()}>
          <LogOut />
          <span className="hidden md:flex">LogOut</span>
        </Button>
      </div>
    </div>
  );
}

export default SideMenuBar;
