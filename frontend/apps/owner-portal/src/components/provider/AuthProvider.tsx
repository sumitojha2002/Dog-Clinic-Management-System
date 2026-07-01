import {
  createContext,
  useContext,
  useEffect,
  useRef,
  useState,
  type PropsWithChildren,
} from "react";
import type { TokenPayload } from "../../types/User";
import axios from "axios";
import { decodeToken } from "../../utils/helpers";

interface AuthContextType {
  accessToken: string | null;
  setAccessToken: (token: string | null) => void;
  isAuthenticated: boolean;
  role: string | null;
  user: TokenPayload | null;
  logout: () => void;
  loading: boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

type AuthProviderProps = PropsWithChildren;

export const useAuth = () => {
  const cxt = useContext(AuthContext);
  if (!cxt) throw new Error("useAuth must be used inside AuthProvicer");
  return cxt;
};

export default function AuthProvider({ children }: AuthProviderProps) {
  const [accessToken, setAccessToken] = useState<string | null>("");
  const [user, setUser] = useState<TokenPayload | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  const didInit = useRef(false);

  useEffect(() => {
    if (accessToken) {
      const payload = decodeToken<TokenPayload>(accessToken);
      setUser(payload);
    } else {
      setUser(null);
    }
  }, [accessToken]);

  const logout = async () => {
    const res = await axios.post(
      "http://localhost:9090/auth/logout",
      {},
      { withCredentials: true },
    );
    const data = res.data;
    setAccessToken(null);
    setUser(null);
  };

  const refreshAccessToken = async () => {
    try {
      const res = await axios.post(
        "http://localhost:9090/auth/refresh",
        {},
        { withCredentials: true },
      );
      const data = res.data;
      setAccessToken(data.accessToken);
    } catch (error) {
      console.error("Token refresh failed:", error);
      setAccessToken(null);
      setUser(null);
      throw new Error("NOT authorized");
    }
  };

  useEffect(() => {
    if (didInit.current) return;
    didInit.current = true;
    const initAuth = async () => {
      try {
        await refreshAccessToken();
      } catch {
        setAccessToken(null);
        setUser(null);
      } finally {
        setLoading(false);
      }
    };
    initAuth();
  }, []);

  return (
    <AuthContext.Provider
      value={{
        accessToken,
        setAccessToken,
        isAuthenticated: !!accessToken,
        role: user?.role ?? null,
        user,
        logout,
        loading,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}
