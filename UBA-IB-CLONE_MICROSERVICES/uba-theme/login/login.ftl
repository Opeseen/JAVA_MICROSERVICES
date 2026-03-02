<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password') displayInfo=realm.password && realm.registrationAllowed && !registrationDisabled??; section>

  <#if section = "header">
    ${msg("loginTitle",(realm.displayName!''))}

  <#elseif section = "form">

    <!-- UBA Logo -->
    <div class="uba-logo">
      <span>UBA</span>
      <p>United Bank for Africa</p>
    </div>

    <#if messagesPerField.existsError('username','password')>
      <div class="kcFeedbackErrorClass">
        ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
      </div>
    </#if>

    <form id="kc-form-login" action="${url.loginAction}" method="post">

      <!-- Email -->
      <div class="kcFormGroupClass">
        <label for="username" class="kcLabelClass">Email Address</label>
        <input
          id="username"
          name="username"
          type="email"
          autofocus
          autocomplete="off"
          class="kcInputClass"
          placeholder="john@example.com"
          value="${(login.username!'')}"
        />
      </div>

      <!-- Password -->
      <div class="kcFormGroupClass" style="margin-top: 16px;">
        <label for="password" class="kcLabelClass">Password</label>
        <input
          id="password"
          name="password"
          type="password"
          autocomplete="off"
          class="kcInputClass"
          placeholder="Enter your password"
        />
      </div>

      <!-- Forgot Password -->
      <#if realm.resetPasswordAllowed>
        <div id="kc-forgot-password" style="text-align: right; margin-top: 8px;">
          <a href="${url.loginResetCredentialsUrl}">Forgot password?</a>
        </div>
      </#if>

      <!-- Submit -->
      <div style="margin-top: 20px;">
        <input
          type="submit"
          id="kc-login"
          value="${msg("doLogIn")}"
        />
      </div>

    </form>

    <!-- Register Link -->
    <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
      <div id="kc-registration" style="text-align: center; margin-top: 16px; font-size: 13px; color: #6b7280;">
        Don't have an account?
        <a href="${url.registrationUrl}">Create one</a>
      </div>
    </#if>

  </#if>
</@layout.registrationLayout>