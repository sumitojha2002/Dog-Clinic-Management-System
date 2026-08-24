import { Route, Routes } from "react-router-dom";
import "./App.css";
import MainLayout from "./components/layouts/MainLayout";
import Appointment from "./pages/Appointment";
import Veterinarian from "./pages/Veterinarian";
import Dogs from "./pages/Dogs";
import Profile from "./pages/Profile";
import Dashboard from "./pages/Dashborad";

function App() {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<Dashboard />} />
        <Route path="/appointments" element={<Appointment />} />
        <Route path="/veterinarian" element={<Veterinarian />} />
        <Route path="/dogs" element={<Dogs />} />
        <Route path="/profile" element={<Profile />} />
      </Route>
    </Routes>
  );
}

export default App;
