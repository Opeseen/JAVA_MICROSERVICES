export interface User {
  id: string;
  username?: string;
  firstName: string;
  lastName: string;
  email: string;
  role: string[];
}

export interface RegisterPayload {
  firstName: string;
  lastName: string;
  email: string;
  bvn: string;
  phone?: string;
  accountType: "savings" | "current";
}
