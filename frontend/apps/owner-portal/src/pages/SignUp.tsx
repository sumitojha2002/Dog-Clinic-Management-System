import { ChevronLeft } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { Button } from "../components/ui/button";
import { useMutation } from "@tanstack/react-query";
import { signup } from "../services/api/authapi";
import type { AxiosError } from "axios";
import type { BackendError, Errors } from "../services/api/apitypes";
import type z from "zod";
import { signupSchema } from "../utils/helpers";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";

type SignUpForm = z.infer<typeof signupSchema>;

function SignUp() {
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors, isSubmitting },
  } = useForm<SignUpForm>({
    resolver: zodResolver(signupSchema),
  });

  const navigate = useNavigate();

  const mutation = useMutation({
    mutationFn: (formdata: FormData) => signup(formdata),
    onSuccess: (data) => {
      alert(data.message);
      navigate("/login");
    },
    onError: (error: AxiosError<BackendError<Errors>>) => {
      const backendErrors = error.response?.data.errors;

      if (error.response?.data.message)
        setError("root", { message: error.response.data.message });

      if (backendErrors?.username)
        setError("username", { message: backendErrors.username });

      if (backendErrors?.email)
        setError("email", { message: backendErrors.email });

      if (backendErrors?.password)
        setError("password", { message: backendErrors.password });
    },
  });

  const formSubmit = (data: SignUpForm) => {
    const formData = new FormData();
    console.log(data);
    formData.append("username", data.username);
    formData.append("email", data.email);
    formData.append("password", data.password);

    mutation.mutate(formData);
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
      <form
        onSubmit={handleSubmit(formSubmit)}
        className="text-center flex flex-col gap-6"
      >
        <div className="grid grid-cols-2">
          <label>Username</label>

          <input type="text" className="border-b" {...register("username")} />
        </div>
        {errors.username && (
          <div className="grid grid-cols-2">
            <div></div>
            <div>
              <p className="text-red-600 text-[12px] text-left">
                {" "}
                * {errors.username.message}
              </p>
            </div>
          </div>
        )}
        <div className="grid grid-cols-2">
          <label>Email</label>

          <input type="text" className="border-b" {...register("email")} />
        </div>
        {errors.email && (
          <div className="grid grid-cols-2">
            <div></div>
            <div>
              <p className="text-red-600 text-[12px] text-left">
                {" "}
                * {errors.email.message}
              </p>
            </div>
          </div>
        )}
        <div className="grid grid-cols-2">
          <label>Password</label>
          <input
            type="password"
            className="border-b"
            {...register("password")}
          />
        </div>
        {errors.password && (
          <div className="grid grid-cols-2">
            <div></div>
            <div>
              <p className="text-red-600 text-[12px] text-left">
                {" "}
                * {errors.password.message}
              </p>
            </div>
          </div>
        )}
        {errors.root && <p className="text-red-600">{errors.root.message}</p>}
        <Button
          className="w-full mt-4"
          type="submit"
          disabled={mutation.isPending || isSubmitting}
        >
          {mutation.isPending ? "Signing in...." : "Sign up"}
        </Button>
      </form>
    </div>
  );
}

export default SignUp;
