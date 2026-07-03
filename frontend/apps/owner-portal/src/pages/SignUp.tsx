import { ChevronLeft } from "lucide-react";
import React, { useState, type FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "../components/ui/button";
import { useMutation } from "@tanstack/react-query";
import { signup } from "../services/api/authapi";
import type { AxiosError } from "axios";
import type { BackendError, Errors } from "../services/api/apitypes";

function SignUp() {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [email, setEmail] = useState<string>("");

  const navigate = useNavigate();

  //errors
  const [error, setError] = useState<string | undefined>("");
  const [usernameError, setUsernameError] = useState<string | undefined>("");
  const [emailError, setEmailError] = useState<string | undefined>("");
  const [passError, setPassError] = useState<string | undefined>("");

  const mutation = useMutation({
    mutationFn: signup,
    onSuccess: (data) => {
      alert(data.message);
      navigate("/login");
    },
    onError: (error: AxiosError<BackendError<Errors>>) => {
      if (error.response?.data.message) setError(error.response?.data?.message);

      if (error.response?.data.errors?.username)
        setUsernameError(error.response.data.errors.username);

      if (error.response?.data.errors?.email)
        setEmailError(error.response.data.errors.email);

      if (error.response?.data.errors?.password)
        setPassError(error.response.data.errors.password);
    },
  });

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();

    mutation.mutate({
      username,
      password,
      email,
    });

    setUsername("");
    setPassword("");
    setEmail("");
  };

  return (
    <div className=" border w-100 p-3">
      <ChevronLeft
        size={30}
        onClick={() => navigate("/")}
        className="cursor-pointer"
      />
      <div>
        <h1 className="text-5xl m-10">Sign up</h1>
      </div>
      <form onSubmit={handleSubmit} className="text-center flex flex-col gap-6">
        <div className="grid grid-cols-2">
          <label>Username</label>

          <input
            type="text"
            className="border-b"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        {usernameError && (
          <div className="grid grid-cols-2">
            <div></div>
            <div>
              <p className="text-red-600 text-[12px] text-left">
                {" "}
                * {usernameError}
              </p>
            </div>
          </div>
        )}
        <div className="grid grid-cols-2">
          <label>Email</label>

          <input
            type="text"
            className="border-b"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        {emailError && (
          <div className="grid grid-cols-2">
            <div></div>
            <div>
              <p className="text-red-600 text-[12px] text-left">
                {" "}
                * {emailError}
              </p>
            </div>
          </div>
        )}
        <div className="grid grid-cols-2">
          <label>Password</label>
          <input
            type="password"
            className="border-b"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        {passError && (
          <div className="grid grid-cols-2">
            <div></div>
            <div>
              <p className="text-red-600 text-[12px] text-left">
                {" "}
                * {passError}
              </p>
            </div>
          </div>
        )}
        {error && <p className="text-red-600">{error}</p>}
        <Button
          className="w-full mt-4"
          type="submit"
          disabled={mutation.isPending}
        >
          {mutation.isPending ? "Signing in...." : "Sign up"}
        </Button>
      </form>
    </div>
  );
}

export default SignUp;
