<#import "template.ftl" as layout>

<@layout.registrationLayout displayMessage=!messagesPerField.existsError('username','password'); section>

  <#if section = "form">
  <div class="page">

    <div class="main-layout">

      <!-- LEFT: Branding -->
      <div class="left-panel">
        <div class="brand-logo">
          <div class="logo-uba">UBA</div>
          <div class="logo-sub">United Bank for Africa</div>
        </div>

        <h1 class="brand-headline">
          Welcome,<br />
          <span class="brand-highlight">Let's Bank</span>
        </h1>
        <p class="brand-sub">
          With footprints in 23 countries, we bring<br />
          you ease of financial transactions.
        </p>

        <div class="brand-features">
          <div class="feature-item">
            <span class="feature-dot"></span>
            Pan-African reach across 23 countries
          </div>
          <div class="feature-item">
            <span class="feature-dot"></span>
            Bank-grade encryption &amp; security
          </div>
          <div class="feature-item">
            <span class="feature-dot"></span>
            Instant transfers, 24 / 7 availability
          </div>
        </div>
      </div>

      <!-- RIGHT: Login Card -->
      <div class="right-panel">
        <div class="login-card">

          <div class="card-header">
            <span class="card-title">UBA INTERNET BANKING</span>
          </div>

          <div class="card-body">

            <#if messagesPerField.existsError('username','password')>
              <div class="alert-error">
                <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor" viewBox="0 0 16 16">
                  <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                  <path d="M7.002 11a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 4.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 4.995z"/>
                </svg>
                <span>${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}</span>
              </div>
            </#if>

            <div class="locale-row">
              <div class="locale-sel">
                <span class="flag">🇳🇬</span>
                <select name="country" id="country">
                  <option value="nigeria">Nigeria</option>
                  <option value="ghana">Ghana</option>
                  <option value="kenya">Kenya</option>
                  <option value="uganda">Uganda</option>
                </select>
              </div>
              <div class="locale-sel">
                <select name="language" id="language">
                  <option value="en">English</option>
                  <option value="fr">French</option>
                </select>
              </div>
            </div>

            <form id="kc-form-login" action="${url.loginAction}" method="post">

              <input type="hidden" name="login" value="true" />

              <!-- Username -->
              <div class="form-group">
                <label for="username" style="display:none;"></label>
                <div class="input-wrap <#if messagesPerField.existsError('username')>input-error</#if>">
                  <input
                    type="text"
                    id="username"
                    name="username"
                    placeholder="User ID"
                    value="${(login.username!'')}"
                    autocomplete="username"
                    autofocus
                    required
                  />
                </div>
                <p class="input-hint">If Corporate, format is Corp ID.User ID</p>
              </div>

              <!-- Password -->
              <div class="form-group">
                <label for="password" style="display:none;"></label>
                <div class="input-wrap <#if messagesPerField.existsError('password')>input-error</#if>">
                  <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Password"
                    autocomplete="current-password"
                    required
                  />
                  <span class="toggle-pw" onclick="togglePassword()" title="Show/hide password">
                    <svg id="eye-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="#bbb" viewBox="0 0 16 16">...</svg>
                  </span>
                </div>
              </div>

              <input type="hidden" id="id-hidden-input" name="credentialId"
                <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if> />

              <button type="submit" id="kc-login" class="login-btn">
                LOGIN
              </button>

            </form>

            <a href="http://localhost:5000/auth/ibanking/register" class="register-btn">
              Instant Self-Registration
            </a>

          </div>
        </div>

      </div>
    </div>

  </div>

  <script>
    function togglePassword() {
      var input = document.getElementById('password');
      var icon  = document.getElementById('eye-icon');

      if (!input) return;

      var show = input.type === 'password';
      input.type = show ? 'text' : 'password';

      if (icon) {
        icon.setAttribute('fill', show ? '#c8102e' : '#bbb');
      }
    }
  </script>

  </#if>

</@layout.registrationLayout>