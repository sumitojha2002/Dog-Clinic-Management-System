export interface Response<T = void> {
  message: string;
  status: string;
  data?: T;
}

export interface accessToken {
  accessToken: string;
}

export interface Login {
  username: string;
  password: string;
}

export interface BackendError<T = void> {
  message: string;
  state: string;
  throwable: string | null;
  errors?: T;
}

export interface VetsData {
  vetId: number;
  name: string;
  imageURL: string;
  specialization: string[];
}

export interface AppointmentInfo {
  id: number;
  dogs: dogInfo;
  reason: string;
  appointmentStatus: string;
  vets: vetInfo;
  ownersProfile: ownersProfile;
  appointmentDate: string;
  appLocalTime: string;
}

export interface ownersProfile {
  user: user;
  ownerId: number;
  name: string | null;
  phoneNumber: null | string;
  alternatePhoneNumber: null | string;
  address: string | null;
  registrationDate: string;
}

export interface vetInfo {
  user: user;
  vetId: number;
  name: string;
  licenseNumber: string;
  specialization: string[];
  yearOfExperience: number;
}

export interface user {
  username: string;
  email: string;
}

export interface dogInfo {
  id: number;
  name: string;
  imageUrl: null | string;
  breed: string;
  gender: string;
  color: string;
  weight: number;
  dateOfBirth: string;
  vactionationStatus: string;
  allergies: string[];
  chronicConditions: string[];
  registeredDate: string;
  lastVisitDate: string | null;
  status: null | string;
}

export interface vetList {
  vetId: number;
  name: string;
}
