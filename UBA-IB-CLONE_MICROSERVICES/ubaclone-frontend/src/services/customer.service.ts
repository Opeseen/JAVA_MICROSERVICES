import apiClient from "@/api/apiClient";

export const customerService = {
  async fetchCustomerInfo(email: string) {
    try {
      const response = await apiClient.get(
        "/ubaclone/accounts/api/customerDetails",
        { params: { email } },
      );
      return response.data;
    } catch (error) {
      throw new Error("Failed");
    }
  },
};
