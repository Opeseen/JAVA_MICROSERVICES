<script setup lang="ts">
import { ref } from "vue";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { toast } from "vue-sonner";
import { authService } from "@/services/auth.service";
import { RouterLink, useRouter } from "vue-router";

const router = useRouter();
const isLoading = ref(false);

interface RegisterForm {
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  bvn: string;
  accountType: "savings" | "current";
}

const form = ref<RegisterForm>({
  firstName: "",
  lastName: "",
  email: "",
  phone: "",
  bvn: "",
  accountType: "" as "savings" | "current",
});

async function handleSubmit() {
  try {
    isLoading.value = true;
    await authService.register({
      firstName: form.value.firstName,
      lastName: form.value.lastName,
      email: form.value.email,
      phone: form.value.phone,
      bvn: form.value.bvn,
      accountType: form.value.accountType,
    });
    toast.success("Account created successfully!");
    router.push("/dashboard/overview");
  } catch (error) {
    toast.error("Registration failed");
  } finally {
    isLoading.value = false;
  }
}
</script>

<template>
  <div class="min-h-screen">
    <!-- Register Header -->
    <header
      class="border-b border-gray-200 px-8 py-4 flex items-center justify-between"
    >
      <div class="flex items-center gap-3 pl-3">
        <h1 class="text-[30px] font-medium text-gray-800">
          Instant Account Opening
        </h1>
      </div>

      <!-- UBA Logo -->
      <RouterLink to="/">
        <div class="flex flex-col items-end leading-none">
          <div class="flex items-center gap-1">
            <span class="text-[22px] font-extrabold text-red-600 tracking-wider"
              >UBA</span
            >
          </div>
          <span class="text-[9px] text-gray-500 mt-0.5 tracking-wide"
            >United Bank for Africa</span
          >
        </div>
      </RouterLink>
    </header>

    <!-- Form Container -->
    <div class="max-w-2xl mx-auto px-5 py-12">
      <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-8">
        <!-- Form Title -->
        <div class="mb-8">
          <h2 class="text-2xl font-bold text-gray-900">Create your account</h2>
          <p class="text-gray-500 text-sm mt-1">
            Fill in the details below to open your account
          </p>
        </div>

        <!-- Form -->
        <div class="space-y-5">
          <!-- Name Row -->
          <div class="grid grid-cols-2 gap-4">
            <div class="flex flex-col gap-1.5">
              <label class="text-sm font-medium text-gray-700"
                >First Name</label
              >
              <input
                v-model="form.firstName"
                type="text"
                placeholder="John"
                class="w-full border border-gray-300 rounded-lg px-3 py-2.5 text-sm outline-none focus:ring-2 focus:ring-red-500 focus:border-transparent transition"
              />
            </div>
            <div class="flex flex-col gap-1.5">
              <label class="text-sm font-medium text-gray-700">Last Name</label>
              <input
                v-model="form.lastName"
                type="text"
                placeholder="Doe"
                class="w-full border border-gray-300 rounded-lg px-3 py-2.5 text-sm outline-none focus:ring-2 focus:ring-red-500 focus:border-transparent transition"
              />
            </div>
          </div>

          <!-- Email -->
          <div class="flex flex-col gap-1.5">
            <label class="text-sm font-medium text-gray-700"
              >Email Address</label
            >
            <input
              v-model="form.email"
              type="email"
              placeholder="john@example.com"
              class="w-full border border-gray-300 rounded-lg px-3 py-2.5 text-sm outline-none focus:ring-2 focus:ring-red-500 focus:border-transparent transition"
            />
          </div>

          <!-- Phone -->
          <div class="flex flex-col gap-1.5">
            <label class="text-sm font-medium text-gray-700"
              >Phone Number</label
            >
            <input
              v-model="form.phone"
              type="tel"
              placeholder="+234 000 0000 000"
              class="w-full border border-gray-300 rounded-lg px-3 py-2.5 text-sm outline-none focus:ring-2 focus:ring-red-500 focus:border-transparent transition"
            />
          </div>

          <!-- BVN -->
          <div class="flex flex-col gap-1.5">
            <label class="text-sm font-medium text-gray-700">BVN</label>
            <input
              v-model="form.bvn"
              type="text"
              placeholder="Enter your 11-digit BVN"
              maxlength="11"
              class="w-full border border-gray-300 rounded-lg px-3 py-2.5 text-sm outline-none focus:ring-2 focus:ring-red-500 focus:border-transparent transition"
            />
            <p class="text-xs text-gray-400">
              Your BVN is used to verify your identity
            </p>
          </div>

          <!-- Account Type -->
          <div class="flex flex-col gap-1.5">
            <label class="text-sm font-medium text-gray-700"
              >Account Type</label
            >
            <Select v-model="form.accountType">
              <SelectTrigger class="w-full focus:ring-2 focus:ring-red-500">
                <SelectValue placeholder="Select account type" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="savings">Savings Account</SelectItem>
                <SelectItem value="current">Current Account</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <!-- Submit Button -->
          <Button
            class="w-full bg-red-600 hover:bg-red-700 text-white py-2.5 text-sm font-semibold mt-2"
            :disabled="isLoading"
            @click="handleSubmit"
          >
            {{ isLoading ? "Creating Account..." : "Create Account" }}
          </Button>

          <!-- Login Link -->
          <p class="text-center text-sm text-gray-500">
            Already have an account?
            <RouterLink
              to="/auth/login"
              class="text-red-600 hover:underline font-medium"
            >
              Sign in
            </RouterLink>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
