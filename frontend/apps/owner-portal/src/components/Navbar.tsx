import { Link, useNavigate } from "react-router-dom";
import logo from "../assets/logo.png";
import { Button } from "./ui/button";
import { useAuth } from "./provider/AuthProvider";

export default function Navbar() {
  const auth = useAuth();
  const navigate = useNavigate();
  return (
    <div className="flex justify-between">
      <div className=" w-full items-start">
        <img height="70px" width="70px" src={logo} alt="websitelogo" />
      </div>
      <div className="flex justify-between w-full items-center font-semibold">
        <Link to="">Home</Link>
        <Link to="">About</Link>
        <Link to="">Contact</Link>
        <Link to="">Service</Link>
      </div>
      <div className="w-full flex justify-end items-center">
        <div className="gap-3 flex justify-between">
          {auth.isAuthenticated ?
            <>
            <Button>Profile</Button>
              <Button variant={"outline"} onClick={() => auth.logout()}>
                Log Out
              </Button>
            </>
          : <>
              <Button>Sign up</Button>
              <Button variant={"outline"} onClick={() => navigate("/login")}>
                Login
              </Button>
          </>}
        </div>
      </div>
    </div>
  );
}
