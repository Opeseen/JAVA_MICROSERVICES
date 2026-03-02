import Keycloak from "keycloak-js";

const keycloak = new Keycloak({
  url: import.meta.env.VITE_KEYCLOAK_URL,
  realm: import.meta.env.VITE_KEYCLOAK_REALM,
  clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID,
});

let isInitialized = false; // ← tracks if already initialized

export async function initKeycloak(): Promise<boolean> {
  if (isInitialized) {
    return keycloak.authenticated ?? false; // ← just return current state
  }

  try {
    const authenticated = await keycloak.init({
      pkceMethod: "S256",
    });
    isInitialized = true;

    // ← Start auto refresh AFTER successful init
    if (authenticated) {
      startTokenRefresh();
    }

    return authenticated;
  } catch {
    return false;
  }
}

export function logoutKeycloak() {
  isInitialized = false; // ← reset so initKeycloak() can run again after logout
  keycloak.logout({
    redirectUri: window.location.origin,
  });
}

// ← Used in ProtectedRoute.vue — with check-sso to restore session on refresh
export async function initKeycloakSilent(): Promise<boolean> {
  if (isInitialized) {
    return keycloak.authenticated ?? false;
  }

  try {
    const authenticated = await keycloak.init({
      pkceMethod: "S256",
      onLoad: "check-sso",
      checkLoginIframe: false,
    });

    isInitialized = true;

    if (authenticated) {
      startTokenRefresh();
    }

    return authenticated;
  } catch {
    return false;
  }
}

export async function handleLogin(): Promise<void> {
  const authenticated = await initKeycloak();

  if (authenticated) {
    // Already logged in — open dashboard in new tab
    window.open("/dashboard", "_blank");
    return;
  }

  // Generate Keycloak login URL and open in new tab
  const loginUrl = await keycloak.createLoginUrl({
    redirectUri: window.location.origin + "/dashboard",
  });

  window.open(loginUrl, "_blank");
}

// Auto refresh token every 60 seconds
function startTokenRefresh() {
  setInterval(async () => {
    try {
      // Refresh token if it expires within 60 seconds
      const refreshed = await keycloak.updateToken(60);
      if (refreshed) {
        console.log("Token refreshed");
      }
    } catch {
      // Refresh failed — session expired, force logout
      keycloak.logout({
        redirectUri: window.location.origin + "/auth/login",
      });
    }
  }, 60000); // ← runs every 60 seconds
}

export default keycloak;
