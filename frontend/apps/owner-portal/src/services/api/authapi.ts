import axios from "axios";
import { z } from "zod";
import type {
  DogCardInfo,
  DogPostProfile,
  DogsProfile,
  Login,
  OwnerProfile,
  Response,
  ResponseLogin,
  SignUp,
  UserUpdateProfileInfo,
} from "./apitypes";
import type { dogInfoSchema } from "../../utils/helpers";

const baseUrl = "http://localhost:9090";
// use to login
export const login = async (
  loginInfo: Login,
): Promise<Response<ResponseLogin>> => {
  const response = await axios.post<Response<ResponseLogin>>(
    baseUrl + `/auth/login`,
    loginInfo,
    { withCredentials: true },
  );
  return response.data;
};

// sign up
export const signup = async (signupInfo: SignUp): Promise<Response> => {
  const response = await axios.post<Response>(
    baseUrl + `/auth/register/petowner`,
    signupInfo,
    { withCredentials: true },
  );
  return response.data;
};

// get owner profile
export const getOwnerProfile = async (
  token: string | undefined,
): Promise<Response<OwnerProfile>> => {
  const response = await axios.get<Response<OwnerProfile>>(
    baseUrl + "/owner/profile",
    { headers: { Authorization: `Bearer ` + token } },
  );
  return response.data;
};

// update owner profile
export const updateOwnerProfile = async (
  token: string | undefined,
  userUpdateProfileInfo: UserUpdateProfileInfo,
): Promise<Response<UserUpdateProfileInfo>> => {
  const responce = await axios.post<Response<UserUpdateProfileInfo>>(
    baseUrl + "/owner/profile",
    userUpdateProfileInfo,
    {
      headers: { Authorization: `Bearer ` + token },
    },
  );
  return responce.data;
};

// get all dogs card info
export const getAllDogsCardInfo = async (
  token: string | undefined,
): Promise<Response<DogCardInfo[]>> => {
  const response = await axios.get<Response<DogCardInfo[]>>(
    baseUrl + "/owner/dogs",
    {
      headers: { Authorization: `Bearer ` + token },
    },
  );
  console.log("API response:", response);
  console.log("API response.data:", response.data);

  return response.data;
};

export const getDogProfileById = async (
  token: string | undefined,
  id: string | undefined,
): Promise<Response<DogsProfile>> => {
  const response = await axios.get<Response<DogsProfile>>(
    baseUrl + `/owner/dogs/${id}`,
    { headers: { Authorization: "Bearer " + token } },
  );
  return response.data;
};

export const postNewDogProfile = async (
  token: string | undefined,
  dogInfo: FormData,
): Promise<Response> => {
  const response = await axios.post<Response>(baseUrl + "/owner/dogs", dogInfo, {
    headers: { Authorization: `Bearer ` + token },
  });
  return response.data;
};
