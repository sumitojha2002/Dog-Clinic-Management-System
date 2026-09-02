import axios from "axios";
import type { accessToken, Login, Response } from "./apitypes";

const baseUrl = "http://localhost:9090";

export const login = async (
  LoginInfo: Login,
): Promise<Response<accessToken>> => {
  const response = await axios.post<Response<accessToken>>(
    baseUrl + `/auth/login`,
    LoginInfo,
    {
      withCredentials: true,
    },
  );
  return response.data;
};
