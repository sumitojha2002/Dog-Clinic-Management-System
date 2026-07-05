export interface Response<T = void> {
  message: string;
  status: string;
  data?: T;
}

export interface DogCardInfo {
  id: number;
  imageURL: null | string;
  name: string;
  dateOfBirth: string;
  breed: string;
}

export interface Login {
  username: string;
  password: string;
}

export interface ResponseLogin {
  accessToken: string;
}

export interface BackendError<T = void> {
  message: string;
  state: string;
  throwable: string | null;
  errors?: T;
}

export interface Errors {
  username: string;
  password: string;
  email: string;
}

export interface SignUp {
  username: string;
  password: string;
  email: string;
}

export interface OwnerProfile {
  user: UserInfo;
  ownerId: number;
  phoneNumber: string | null;
  address: string | null;
  registrationDate: string | undefined;
}

interface UserInfo {
  username: string;
  email: string;
}

export interface UserUpdateProfileInfo {
  phoneNumber: string;
  address: string;
}

export interface DogsProfile {
  id: number;
  name: string;
  imageUrl: null | string;
  breed: string;
  gender: string;
  color: string;
  weight: number | null;
  dateOfBirth: string;
  vactionationStatus: string;
  allergies: string[];
  chronicConditions: string[];
  registeredDate: string;
  lastVisistDate: null | string;
  status: null | string;
}
