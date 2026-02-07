## Steps

### Normal user for (e-service)

- Must already have an account with the bank (if not must create one) ✔️
- Will register for the e-service in keycloak ✔️
- During e-service registration (user must provide their account number for verification)
- Account details information must be verified and saved along with the user information in keycloak
- Must be able to login to the e-service platform to access their accounts and perform transactions (UI features)

### Authenticated flow
Unprotected path ️✔️
- "/api/public/"
- "/ubaclone/accounts/api/public/**"
