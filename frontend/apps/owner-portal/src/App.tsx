import { Route, Routes } from "react-router-dom";
import "./App.css";
import { useAuth } from "./components/provider/AuthProvider";
import ProtectedRoute from "./components/routes/protectedRoutes/ProtectedRouteNavbar";
import MainLayout from "./components/layouts/MainLayout";
import Home from "./pages/Home";
import AuthLayout from "./components/layouts/AuthLayout";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Profile from "./pages/Profile";
import DogsProfilesListUI from "./pages/DogsProfilesListUI";
import DogsProfile from "./pages/DogsProfile";
import DogsProfileAdd from "./pages/DogsProfileAdd";
import ProtectedRouteNavbarNoSwitch from "./components/routes/protectedRoutes/ProtectedRouteNavbarNoSwitchProfile";
import Appointment from "./pages/Appointment";

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
      <Route element={<ProtectedRoute allowedRole="ROLE_OWNER" />}>
        <Route path="/owner/profile" element={<Profile />} />
        <Route path="/dogs/profile" element={<DogsProfilesListUI />} />
        <Route path="/dogs/profile/:id" element={<DogsProfile />} />
      </Route>
      <Route
        element={<ProtectedRouteNavbarNoSwitch allowedRole="ROLE_OWNER" />}
      >
        <Route path="/dogs/:id/appointment/new" element={<Appointment />} />
        <Route path="/dogs" element={<DogsProfileAdd />} />
      </Route>
    </Routes>
  );
}

export default App;
