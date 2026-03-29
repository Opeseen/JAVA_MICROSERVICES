import { createRouter, createWebHistory } from "vue-router";
import ProtectedRoute from "@/pages/ProtectedRoute.vue";
import Home from "@/pages/Home.vue";
import IBankingLogin from "@/pages/auth/IBankingLogin.vue";
import Register from "@/pages/auth/Register.vue";
import Overview from "@/pages/dashboard/Overview.vue";
import Settings from "@/pages/dashboard/Settings.vue";
import NotFound from "@/components/errors/NotFound.vue";
import IBankingregister from "@/pages/auth/IBankingregister.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // Home page (with Header + Footer)
    {
      path: "/",
      component: Home,
    },

    // Auth page
    {
      path: "/auth",
      children: [
        { path: "login", component: IBankingLogin },
        {
          path: "register",
          component: Register,
        },
        {
          path: "ibanking/register",
          component: IBankingregister,
        },
      ],
    },

    // Dashboard pages (protected)
    {
      path: "/dashboard",
      component: ProtectedRoute,
      children: [
        { path: "", component: Overview },
        {
          path: "settings",
          component: Settings,
        },
      ],
    },

    { path: "/:catchAll(.*)", component: NotFound },
  ],
});

export default router;
