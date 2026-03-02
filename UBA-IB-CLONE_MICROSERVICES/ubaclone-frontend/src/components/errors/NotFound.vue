<script setup lang="ts">
import { useRouter } from "vue-router";
import { Button } from "@/components/ui/button";
import { Home, MoveLeft } from "lucide-vue-next";

const router = useRouter();
</script>

<template>
  <div
    class="bg-black min-h-screen flex flex-col items-center justify-center text-white px-5"
  >
    <!-- 404 Big Text -->
    <h1 class="text-[120px] font-extrabold text-red-600 leading-none">404</h1>

    <!-- Message -->
    <h2 class="text-2xl font-bold mt-4 mb-2">Page Not Found</h2>
    <p class="text-[#999] text-sm text-center max-w-sm mb-8">
      The page you are looking for doesn't exist or has been moved.
    </p>

    <!-- Buttons -->
    <div class="flex items-center gap-3">
      <Button
        variant="outline"
        class="bg-transparent border-[#444] text-white hover:border-white gap-2"
        @click="router.back()"
      >
        <MoveLeft :size="16" />
        Go Back
      </Button>

      <Button
        class="bg-red-600 hover:bg-red-700 text-white gap-2"
        @click="router.push('/')"
      >
        <Home :size="16" />
        Go Home
      </Button>
    </div>
  </div>
</template>
