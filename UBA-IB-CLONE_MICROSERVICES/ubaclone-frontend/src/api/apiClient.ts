import axios from "axios";
import { useAuthStore } from "@/store/authStore";
import { toast } from "vue-sonner";

const apiClient = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  timeout: 20000,
});

// Request interceptor - Add auth token
apiClient.interceptors.request.use(
  (config) => {
    const authStore = useAuthStore();
    const token = authStore.state.token;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => Promise.reject(error),
);

// Response interceptor - Handle errors globally
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const status = error.response?.status;

    if (status === 401) {
      const authStore = useAuthStore();
      authStore.logout(); // ← calls your logout action
      toast.error("Session expired. Please login again.");
      window.location.href = "/auth/login";
    } else if (status === 403) {
      toast.error("You are not authorized to perform this action.");
    } else if (status === 404) {
      toast.error("Resource not found.");
    } else if (status === 500) {
      toast.error("Server error. Please try again later.");
    } else if (!error.response) {
      toast.error("Network error. Check your internet connection.");
    }

    return Promise.reject(error);
  },
);

export default apiClient;
