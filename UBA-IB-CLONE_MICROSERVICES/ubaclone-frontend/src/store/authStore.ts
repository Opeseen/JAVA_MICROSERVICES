import { defineStore } from "pinia";
import { ref, computed } from "vue";
import type { User } from "@/types/user";
import { logoutKeycloak } from "@/services/keycloak.service";

interface AuthState {
  user: User | null;
  token: string | null;
}

export const useAuthStore = defineStore("authStore", () => {
  // --- State ---
  const state = ref<AuthState>({
    user: null,
    token: null,
  });

  // --- Getters ---
  const isLoggedIn = computed(() => !!state.value.token);

  // --- Actions ---
  function setUser(userData: User, authToken: string) {
    state.value.user = userData;
    state.value.token = authToken;
  }

  function logout() {
    state.value.user = null;
    state.value.token = null;
    // Reset isInitialized + logout from Keycloak
    logoutKeycloak();
  }

  return { state, isLoggedIn, setUser, logout };
});
