<script setup lang="ts">
import { Button } from "@/components/ui/button";
import keycloak, { initKeycloak } from "@/services/keycloak.service";
import { useRouter } from "vue-router";

const router = useRouter();

async function handleLogin() {
  const authenticated = await initKeycloak();

  if (authenticated) {
    // Already logged in — go straight to dashboard
    router.push("/dashboard");
    return;
  }
  keycloak.login({
    redirectUri: window.location.origin + "/dashboard",
  });
}
</script>

<template>
  <div class="min-h-screen bg-black flex items-center justify-center">
    <div class="bg-white rounded-xl p-8 w-full max-w-sm text-center space-y-6">
      <!-- Logo -->
      <div class="flex flex-col items-center">
        <span class="text-[28px] font-extrabold text-red-600">UBA</span>
        <span class="text-[10px] text-gray-400 uppercase tracking-wide"
          >United Bank for Africa</span
        >
      </div>

      <div>
        <h1 class="text-xl font-bold text-gray-900">Welcome Back</h1>
        <p class="text-gray-500 text-sm mt-1">Click below to securely login</p>
      </div>

      <Button
        class="w-full bg-red-600 hover:bg-red-700 text-white"
        @click="handleLogin"
      >
        Login to Internet Banking
      </Button>

      <p class="text-sm text-gray-500">
        Don't have an account?
        <RouterLink
          to="/auth/register"
          class="text-red-600 hover:underline font-medium"
        >
          Create one
        </RouterLink>
      </p>
    </div>
  </div>
</template>
