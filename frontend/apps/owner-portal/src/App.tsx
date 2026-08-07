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
import NewAppointment from "./pages/NewAppointment";
import ViewAppointment from "./pages/ViewAppointment";
import CheckOutPage from "./pages/CheckOutPage";
import ProductLayout from "./components/layouts/ProductLayout";
import ProductDetailPage from "./pages/ProductDetailPage";
import BrowserLayout from "./components/layouts/BrowserLayout";
import BrowserProductPage from "./pages/BrowserProductPage";

function App() {
  const { loading } = useAuth();

  if (loading) {
    return <div>loading...</div>;
  }
  return (
    <Routes>
      {/*  main layout */}
      <Route element={<MainLayout />}>
        <Route path="/" element={<Home />} />
        <Route path="/checkout/:id" element={<CheckOutPage />} />
      </Route>
      {/*  auth layout */}
      <Route element={<AuthLayout />}>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
      </Route>
      {/*  protected route */}
      <Route element={<ProtectedRoute allowedRole="ROLE_OWNER" />}>
        <Route path="/owner/profile" element={<Profile />} />
        <Route path="/dogs/profile" element={<DogsProfilesListUI />} />
        <Route path="/dogs/profile/:id" element={<DogsProfile />} />
      </Route>
      <Route
        element={<ProtectedRouteNavbarNoSwitch allowedRole="ROLE_OWNER" />}
      >
        <Route path="/dogs/:id/appointment/new" element={<NewAppointment />} />
        <Route path="/dogs" element={<DogsProfileAdd />} />
        <Route path="/dogs/:id/appointment" element={<ViewAppointment />} />
      </Route>

      {/* Product Layout */}
      <Route element={<ProductLayout />}>
        <Route path="/products/:id" element={<ProductDetailPage />} />
      </Route>

      <Route element={<BrowserLayout />}>
        <Route path="/products" element={<BrowserProductPage />} />
      </Route>
    </Routes>
  );
}

export default App;
