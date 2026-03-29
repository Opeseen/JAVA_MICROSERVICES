<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { toast } from "vue-sonner";
import IbankingHeader from "@/common/IbankingHeader.vue";
import { Button } from "@/components/ui/button";
import { Loader2 } from "lucide-vue-next";

const router = useRouter();
const isLoading = ref(false);

const form = ref({
  accountNumber: "",
  referralId: "",
});

const errors = ref({
  accountNumber: "",
});

function validate() {
  let valid = true;

  if (!form.value.accountNumber) {
    errors.value.accountNumber = "Account number is required";
    valid = false;
  } else if (!/^\d{10}$/.test(form.value.accountNumber)) {
    errors.value.accountNumber = "Account number must be 10 digits";
    valid = false;
  } else {
    errors.value.accountNumber = "";
  }

  return valid;
}

function handleContinue() {
  if (!validate()) return;
  isLoading.value = true;

  try {
    // go to next step
    // router.push("/next-step");
    // Okay.. so the handle continue will make an api call to the backend and return a data containing the first and last name..
    // So.. if data is successfully returned i wou;ld wanna transition to the next screen
  } catch (error: any) {
    toast.error(error.message);
  } finally {
    isLoading.value = false;
  }
}

function handleBack() {
  router.back();
}
</script>

<template>
  <div class="min-h-screen bg-gray-100 flex flex-col items-center py-10">
    <!-- Header -->
    <div class="w-full max-w-3xl flex justify-between items-center mb-6 px-2">
      <IbankingHeader />
    </div>

    <!-- Card -->
    <div
      class="w-full max-w-3xl bg-white border border-gray-300 rounded shadow-sm p-6"
    >
      <!-- Info text -->
      <p class="text-sm text-gray-600 mb-4">
        To register, please provide your Account Number below.
      </p>

      <!-- Form -->
      <div class="space-y-5">
        <!-- Account Number -->
        <div class="flex flex-col">
          <label class="text-sm text-gray-700 mb-1">
            Account Number<span class="text-red-500">*</span>
          </label>

          <input
            v-model="form.accountNumber"
            type="text"
            maxlength="10"
            placeholder="Enter 10-digit account number"
            class="border border-gray-300 rounded px-3 py-2 text-sm outline-none focus:ring-2 focus:ring-red-500"
          />

          <p v-if="errors.accountNumber" class="text-xs text-red-500 mt-1">
            {{ errors.accountNumber }}
          </p>
        </div>

        <!-- Referral ID -->
        <div class="flex flex-col">
          <label class="text-sm text-gray-700 mb-1">
            Referral ID (Optional)
          </label>

          <input
            v-model="form.referralId"
            type="text"
            placeholder="Enter referral ID"
            class="border border-gray-300 rounded px-3 py-2 text-sm outline-none focus:ring-2 focus:ring-red-500"
          />
        </div>

        <!-- Buttons -->
        <div class="flex justify-end gap-3 mt-6">
          <Button @click="handleBack" class="px-4 py-2 text-sm rounded">
            Back
          </Button>

          <Button
            @click="handleContinue"
            :disabled="isLoading"
            class="px-4 py-2 text-sm bg-red-600 text-white hover:bg-red-700 rounded"
          >
            <Loader2 v-if="isLoading" class="w-4 h-4 animate-spin" />
            {{ isLoading ? "Verifying..." : "Continue" }}
          </Button>
        </div>
      </div>
    </div>
  </div>
</template>
