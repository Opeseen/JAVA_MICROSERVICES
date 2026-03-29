import apiClient from "@/api/apiClient";
import type { RegisterPayload } from "@/types/user";

export const authService = {
  async register(payload: RegisterPayload) {
    try {
      const response = await apiClient.post("/public/onboard", payload);
      return response.data;
    } catch (error) {
      throw new Error("Failed");
    }
  },
};
