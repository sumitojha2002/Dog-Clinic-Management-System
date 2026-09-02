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
