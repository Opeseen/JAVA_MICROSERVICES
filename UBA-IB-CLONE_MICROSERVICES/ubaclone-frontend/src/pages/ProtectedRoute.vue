<script setup lang="ts">
import { useAuthStore } from "@/store/authStore";
import { useRouter } from "vue-router";
import { onMounted } from "vue";
import keycloak, { initKeycloakSilent } from "@/services/keycloak.service";

const authStore = useAuthStore();
const router = useRouter();

onMounted(async () => {
  try {
    const authenticated = await initKeycloakSilent();

    if (authenticated) {
      authStore.setUser(
        {
          id: keycloak.tokenParsed?.sub ?? "",
          firstName: keycloak.tokenParsed?.given_name ?? "",
          lastName: keycloak.tokenParsed?.family_name ?? "",
          email: keycloak.tokenParsed?.email ?? "",
          username: keycloak.tokenParsed?.preferred_username ?? "",
          role: keycloak.tokenParsed?.realm_access?.roles ?? ["user"],
        },
        keycloak.token!,
      );
    } else {
      router.push("/auth/login");
    }
  } catch {
    router.push("/auth/login");
  }
});
</script>

<template>
  <main class="flex-1">
    <div class="h-full">
      <RouterView v-if="authStore.state.token" />
      <div
        v-else
        class="fixed inset-0 flex items-center justify-center bg-white"
      >
        <div class="flex flex-col items-center gap-3">
          <div
            class="w-10 h-10 rounded-full border-4 border-t-red-500 animate-spin"
          />
          <p class="text-lg text-red-600 tracking-wide">Loading...</p>
        </div>
      </div>
    </div>
  </main>
</template>
