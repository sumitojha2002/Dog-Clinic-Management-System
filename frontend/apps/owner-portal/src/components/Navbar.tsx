import { Link } from "react-router-dom";
import logo from "../assets/logo.png";
import { Button } from "./ui/button";

export default function Navbar() {
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
          <Button>Sign up</Button>
          <Button variant={"outline"}>login</Button>
        </div>
      </div>
    </div>
  );
}
