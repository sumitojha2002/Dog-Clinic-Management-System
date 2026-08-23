export interface TokenPayload {
  sub: string;
  role?: string;
  iat: number;
  exp: number;
}
