import { useNavigate, useLocation } from "react-router-dom";
import { Button } from "./ui/button";

function ProfileSwitcher() {
  const navigate = useNavigate();
  const { pathname } = useLocation();

  return (
    <div className="flex justify-center gap-3  mt-10 mb-10">
      <div>
        <Button
          disabled={pathname == "/owner/profile"}
          onClick={() => {
            navigate("/owner/profile");
          }}
        >
          Owner Profile
        </Button>
      </div>
      <div>
        <Button
          disabled={pathname == "/dogs/profile"}
          onClick={() => {
            navigate("/dogs/profile");
          }}
        >
          Dogs Profile
        </Button>
      </div>
    </div>
  );
}

export default ProfileSwitcher;
