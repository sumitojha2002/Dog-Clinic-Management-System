export interface Response<T = void> {
  message: string;
  status: string;
  data?: T;
}

export interface PaymentEcom {
  clientSecret: string;
}

export interface productsDetailInfo {
  id: number;
  subCategoryId?: number;
  name: string;
  description: string;
  summery: string;
  cover: string;
  createdAt?: string;
  deletedAt?: string;
  productsSkus: productSkusInfo[];
}

export interface productSkusInfo {
  id: number;
  productId?: number;
  sku: string;
  price: number;
  quantity: number;
  sizeAttributes: attributesInfo;
  colorAttributes: attributesInfo;
  createdAt: string;
  updatedAt: string;
}

export interface attributesInfo {
  id: number;
  productAttributesType: string;
  value: string;
  createdAt?: string;
  deletedAt?: string | null;
}

export interface productsInfo {
  id: number;
  subCategoryId?: number;
  name: string;
  description?: string;
  cover: string;
  createdAt?: string;
  deltedAt?: string;
}

export interface ResponseForTiming {
  message: string;
  status: string;
  data?: string[] | undefined;
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

export interface AppointmentErrors {
  reason: string;
  appointmentDate: string;
  appointmentTime: string;
  vetId: string;
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

export interface DogPostProfile {
  name: string;
  imageUrl: File;
  breed: string;
  gender: string;
  color: string;
  dateOfBirth: string;
}

export interface VetListInfo {
  vetId: number;
  name: string;
}

export interface Timing {
  slot: string;
}

export interface VetsCard {
  vetId: number;
  name: string;
  imageURL: null | string | undefined;
  specialization: string;
}

export interface AppointmentInfo {
  appId: number;
  dogs: AppDog;
  reason: string;
  appointmentStatus: string;
  vetInfo: AppVet;
  appointmentDate: string;
  appLocalTime: string;
}

export interface AppVet {
  vetId: number;
  name: string;
}

export interface AppDog {
  dogId: number;
  name: string;
}
