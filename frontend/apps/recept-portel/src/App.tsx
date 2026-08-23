import { Route, Routes } from "react-router-dom";
import "./App.css";
import MainLayout from "./components/layouts/MainLayout";
import Home from "./pages/Home";

function App() {
  return (
    <Routes>
      <Route element={<MainLayout />}>
        <Route path="/" element={<Home />} />
      </Route>
    </Routes>
  );
}

export default App;
