import axios from "axios";
import type {
  accessToken,
  AppointmentInfo,
  Login,
  Response,
  vetList,
  VetsData,
} from "./apitypes";

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

export const getAllAppointments = async (
  token: string | undefined,
): Promise<Response<AppointmentInfo[]>> => {
  const res = await axios.get<Response<AppointmentInfo[]>>(
    baseUrl + `/receptionist/appointments`,
    { headers: { Authorization: `Bearer ` + token } },
  );
  return res.data;
};

export const getAllVetsList = async (
  token: string | undefined,
): Promise<Response<vetList[]>> => {
  const res = await axios.get<Response<vetList[]>>(
    baseUrl + `/receptionist/vet-list`,
    {
      headers: { Authorization: `Bearer ` + token },
    },
  );
  return res.data;
};
