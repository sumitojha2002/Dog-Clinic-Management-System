import { Route, Routes } from "react-router-dom";
import "./App.css";
import { useAuth } from "./components/provider/AuthProvider";
import ProtectedRoute from "./components/routes/protectedRoutes/ProtectedRoute";
import MainLayout from "./components/layouts/MainLayout";
import Home from "./pages/Home";

function App() {
  const { loading } = useAuth();

  if (loading) {
    return <div>loading...</div>;
  }
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<Home />} />
      </Route>
      <Route element={<ProtectedRoute allowedRole="ROLE_OWNER" />}>
      </Route>
    </Routes>
  );
}

export default App;
