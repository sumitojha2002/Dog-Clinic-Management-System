export function decodeToken<T = Record<string, unknown>>(
  token: string,
): T | null {
  try {
    const payload = token.split(".")[1];
    const decode = atob(payload);
    return JSON.parse(decode);
  } catch {
    return null;
  }
}
