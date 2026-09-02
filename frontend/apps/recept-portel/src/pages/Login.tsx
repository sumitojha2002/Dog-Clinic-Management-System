import { useNavigate } from "react-router-dom";
import { useMutation } from "@tanstack/react-query";
import { login } from "../services/api/authapi";
import { useState, type FormEvent } from "react";
import { useAuth } from "../components/providers/AuthProvider";
import type { AxiosError } from "axios";
import type { BackendError } from "../services/api/apitypes";
import { Button } from "../components/ui/button";
import { decodeToken } from "../utils/helpers";
import type { TokenPayload } from "../types/User";

function Login() {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [errMsg, setErrMsg] = useState<string | undefined>("");
  const auth = useAuth();

  const navigate = useNavigate();

  const { mutate, isPending } = useMutation({
    mutationFn: login,
    onSuccess: (data) => {
      const token = data.data?.accessToken;

      if (!token) {
        setErrMsg("Access Token not received");
        return;
      }

      const payload = decodeToken<TokenPayload>(token);

      auth.setAccessToken(token);

      if (payload?.role === "ROLE_RECEP") {
        alert(data.message);
        navigate("/");
      } else {
        alert("Invalid User");
        auth.logout();
      }
    },

    onError: (error: AxiosError<BackendError>) => {
      setErrMsg(error.response?.data.message);
    },
  });

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    console.log("working");
    if (username.length == 0 && password.length == 0) {
      return alert("username and password cannot be empty");
    }
    mutate({
      username,
      password,
    });
    setUsername("");
    setPassword("");
  };
  return (
    <div className="flex flex-col justify-center items-center h-screen">
      <form onSubmit={handleSubmit} className="flex flex-col border  p-10">
        <div className="text-2xl font-bold pb-10 text-center">Login</div>
        <div className="grid grid-cols-2  mb-5">
          <label>Username</label>
          <div>
            <input
              className="border pl-2"
              type="text"
              placeholder="Enter username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
        </div>
        <div className="grid grid-cols-2  mb-5">
          <label>Password</label>
          <div>
            <input
              className="border pl-2"
              type="password"
              placeholder="Enter password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
        </div>

        <Button variant={"outline"} className="w-full" type="submit">
          {isPending ? "Logging in" : "Login"}
        </Button>
      </form>
      <div className="mt-3">
        <p className="text-red-500">{errMsg}</p>
      </div>
    </div>
  );
}

export default Login;
