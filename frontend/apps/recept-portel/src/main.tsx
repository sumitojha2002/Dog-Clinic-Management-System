import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import AuthProvider from "./components/providers/AuthProvider.tsx";
import { BrowserRouter } from "react-router-dom";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

const queryClinet = new QueryClient();

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <AuthProvider>
      <BrowserRouter>
        <QueryClientProvider client={queryClinet}>
          <App />
        </QueryClientProvider>
      </BrowserRouter>
    </AuthProvider>
  </StrictMode>,
);
