import axios from "axios";
import type { Login, ResponseLogin } from "./apitypes";

const baseUrl = "http://localhost:9090/auth";

// use to login
export const login = async (loginInfo: Login): Promise<ResponseLogin> => {
  const response = await axios.post<ResponseLogin>(
    baseUrl + `/login`,
    loginInfo,
    { withCredentials: true },
  );
  return response.data;
};
