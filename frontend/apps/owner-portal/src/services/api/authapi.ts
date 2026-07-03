import axios from "axios";
import type { Login, Response, ResponseLogin, SignUp } from "./apitypes";

const baseUrl = "http://localhost:9090/auth";

// use to login
export const login = async (
  loginInfo: Login,
): Promise<Response<ResponseLogin>> => {
  const response = await axios.post<Response<ResponseLogin>>(
    baseUrl + `/login`,
    loginInfo,
    { withCredentials: true },
  );
  return response.data;
};

// sign up
export const signup = async (signupInfo: SignUp): Promise<Response> => {
  const response = await axios.post<Response>(
    baseUrl + `/register/petowner`,
    signupInfo,
    { withCredentials: true },
  );
  return response.data;
};
