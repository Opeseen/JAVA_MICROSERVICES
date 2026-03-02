<script setup lang="ts">
import { ref, onErrorCaptured } from "vue";

const hasError = ref(false);
const errorMessage = ref("");

onErrorCaptured((error) => {
  hasError.value = true;
  errorMessage.value = error.message;
  return false; // ← stops the error from propagating further up
});
</script>

<template>
  <!-- If error occurs, show error UI -->
  <div
    v-if="hasError"
    class="flex flex-col items-center justify-center h-screen bg-black text-white gap-4"
  >
    <h1 class="text-4xl font-bold text-red-600">Something went wrong</h1>
    <p class="text-[#999] text-sm">{{ errorMessage }}</p>
    <button
      class="px-6 py-2 bg-red-600 hover:bg-red-700 text-white rounded text-sm"
      @click="hasError = false"
    >
      Try Again
    </button>
  </div>

  <!-- Otherwise render normally -->
  <slot v-else />
</template>
