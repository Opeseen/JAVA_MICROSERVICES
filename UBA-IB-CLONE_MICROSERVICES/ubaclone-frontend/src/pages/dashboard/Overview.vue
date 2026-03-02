<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useAuthStore } from "@/store/authStore";
import { Button } from "@/components/ui/button";
import { LogOut } from "lucide-vue-next";
import { customerService } from "@/services/customer.service";
import { toast } from "vue-sonner";

const authStore = useAuthStore();
const loading = ref(false);
const error = ref(null);

onMounted(async () => {
  try {
    loading.value = true;
    await customerService.fetchCustomerInfo("david@example.com");
  } catch (error: any) {
    error.value = error.message;
    toast.error("Failed to fetch customer info");
  } finally {
    loading.value = false;
  }
});
</script>

<template>
  <header
    class="w-full flex items-center justify-between px-6 py-3 border-b bg-white shadow-sm"
  >
    <span class="font-semibold text-gray-700">Overview</span>

    <Button
      variant="ghost"
      class="text-red-500 hover:text-red-600 hover:bg-red-50 gap-2 ml-auto"
      @click="authStore.logout()"
    >
      <LogOut :size="16" />
      Logout
    </Button>
  </header>

  <main class="p-6">
    <div v-if="loading">Loading...</div>
    <div v-else-if="error">{{ error }}</div>
    <div>Data is returned!</div>
  </main>
</template>
