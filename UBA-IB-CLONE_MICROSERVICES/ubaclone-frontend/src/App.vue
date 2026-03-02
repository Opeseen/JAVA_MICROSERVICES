<script setup lang="ts">
import { RouterView } from "vue-router";
import ErrorBoundary from "@/components/errors/ErrorBoundary.vue";
import { Toaster } from "vue-sonner";
</script>

<template>
  <Toaster position="top-right" richColors />
  <ErrorBoundary>
    <RouterView />
  </ErrorBoundary>
</template>
