import React, { useEffect, useState, type FormEvent } from "react";

import { Button } from "../components/ui/button";
import { ChevronLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { login } from "../services/api/authapi";
import { useAuth } from "../components/provider/AuthProvider";

function Login() {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const auth = useAuth();
  const navigate = useNavigate();

  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: login,
    onSuccess: (data) => {
      auth.setAccessToken(data.accessToken);
      alert("Loged in successfully.");
      navigate("/");
    },
    onError: (error) => {
      console.log("mutation error:", error);
    },
  });

  useEffect(() => {
    console.log("accessToken updated:", auth);
  }, [auth.accessToken]);

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    if (username.length == 0 && password.length == 0) {
      return alert("username and password cannot be empty");
    }

    mutation.mutate({
      username,
      password,
    });
    setUsername("");
    setPassword("");
  };
  return (
    <div className=" border w-100 p-3">
      <ChevronLeft
        size={30}
        onClick={() => navigate("/")}
        className="cursor-pointer"
      />
      <div>
        <h1 className="text-5xl m-10">Login</h1>
      </div>
      <form onSubmit={handleSubmit} className="flex flex-col gap-6">
        <div className="grid grid-cols-2">
          <label>Username</label>
          <input
            type="text"
            className="border-b"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="grid grid-cols-2">
          <label>Password</label>
          <input
            type="password"
            className="border-b"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <Button variant={"outline"}>Login</Button>
      </form>
    </div>
  );
}

export default Login;
