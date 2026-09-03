import axios from "axios";
import type { accessToken, Login, Response, VetsData } from "./apitypes";

const baseUrl = "http://localhost:9090";

export const login = async (
  LoginInfo: Login,
): Promise<Response<accessToken>> => {
  const response = await axios.post<Response<accessToken>>(
    baseUrl + `/auth/receptionist/login`,
    LoginInfo,
    {
      withCredentials: true,
    },
  );
  return response.data;
};

export const getAllVets = async (): Promise<Response<VetsData[]>> => {
  const res = await axios.get<Response<VetsData[]>>(baseUrl + `/auth/mainVets`);
  return res.data;
};
