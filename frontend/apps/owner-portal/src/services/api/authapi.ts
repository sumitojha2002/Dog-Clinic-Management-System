import axios from "axios";

import type {
  AppointmentInfo,
  DogCardInfo,
  DogsProfile,
  Login,
  OwnerProfile,
  Response,
  ResponseForTiming,
  ResponseLogin,
  UserUpdateProfileInfo,
  VetListInfo,
  VetsCard,
} from "./apitypes";

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
export const signup = async (signupInfo: FormData): Promise<Response> => {
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
  const response = await axios.post<Response>(
    baseUrl + "/owner/dogs",
    dogInfo,
    {
      headers: { Authorization: `Bearer ` + token },
    },
  );
  return response.data;
};

export const bookAppointmentDog = async (
  token: string | undefined,
  id: string,
  appInfo: FormData,
): Promise<Response> => {
  const response = await axios.post<Response>(
    baseUrl + `/owner/dogs/${id}/appointment`,
    appInfo,
    {
      headers: { Authorization: `Bearer ` + token },
    },
  );
  return response.data;
};

// fetch all the vets

export const getAllVet = async (
  token: string | undefined,
): Promise<Response<VetListInfo[]>> => {
  const response = await axios.get<Response<VetListInfo[]>>(
    baseUrl + "/owner/vet-list",
    { headers: { Authorization: `Bearer ` + token } },
  );
  return response.data;
};

// fetch timing of the vets

export const getVetTimeings = async (
  token: string | undefined,
  vetId: string,
  localDate: string,
): Promise<ResponseForTiming> => {
  const response = await axios.get<ResponseForTiming>(
    baseUrl + "/owner/available",
    {
      headers: { Authorization: `Bearer ` + token },
      params: { localDate: localDate, vetId: vetId },
    },
  );
  return response.data;
};

// get vets for home page

export const getVetsCardForHome = async (): Promise<Response<VetsCard[]>> => {
  const responce = await axios.get<Response<VetsCard[]>>(
    baseUrl + "/auth/mainVets",
  );
  return responce.data;
};

export const updateDogsProfile = async (
  token: string | undefined,
  id: string,
  formData: FormData,
): Promise<Response> => {
  const response = await axios.patch<Response>(
    baseUrl + `/owner/dogs/${id}`,
    formData,
    { headers: { Authorization: `Bearer ` + token } },
  );
  return response.data;
};

export const getAllAppointments = async (
  token: string | undefined,
  id: string,
): Promise<Response<AppointmentInfo[]>> => {
  const response = await axios.get<Response<AppointmentInfo[]>>(
    baseUrl + `/owner/dogs/${id}/appointment`,
    {
      headers: { Authorization: `Bearer ` + token },
    },
  );
  return response.data;
};


