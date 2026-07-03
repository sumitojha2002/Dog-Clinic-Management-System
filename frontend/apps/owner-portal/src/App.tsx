import { Route, Routes } from "react-router-dom";
import "./App.css";
import { useAuth } from "./components/provider/AuthProvider";
import ProtectedRoute from "./components/routes/protectedRoutes/ProtectedRoute";
import MainLayout from "./components/layouts/MainLayout";
import Home from "./pages/Home";
import AuthLayout from "./components/layouts/AuthLayout";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";

function App() {
  const { loading } = useAuth();

  if (loading) {
    return <div>loading...</div>;
  }
  return (
    <Routes>
      // main layout
      <Route element={<MainLayout />}>
        <Route path="/" element={<Home />} />
      </Route>
      // auth layout
      <Route element={<AuthLayout />}>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
      </Route>
      // protected route
      <Route element={<ProtectedRoute allowedRole="ROLE_OWNER" />}></Route>
    </Routes>
  );
}

export default App;
