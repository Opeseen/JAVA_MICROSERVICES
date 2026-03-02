<script setup lang="ts">
import { RouterLink } from "vue-router";
import {
  Home,
  Search,
  Globe,
  Lock,
  Facebook,
  X,
  Instagram,
  Youtube,
  Linkedin,
} from "lucide-vue-next";
import { Button } from "@/components/ui/button";
import { handleLogin } from "@/services/keycloak.service";

const navItems = [
  { label: "About Us" },
  { label: "Investors" },
  { label: "Our Impact" },
  { label: "Media" },
  { label: "Support" },
  { label: "Global Reach" },
];

const footerLinks = [
  {
    heading: "About Us",
    links: [
      "Who We Are",
      "Our Strategy",
      "Our History",
      "Awards and Achievements",
      "Leadership",
    ],
  },
  {
    heading: "Investors",
    links: [
      "Rights Issue Circular",
      "Investors Relations",
      "Financial Reports",
      "Investor/Analyst Presentations",
      "Share Price Information",
    ],
  },
  {
    heading: "Our Impact",
    links: ["Our Impact", "The UBA Foundation", "Sustainability"],
  },
  {
    heading: "Media Centre",
    links: ["News", "Press Releases", "Podcasts", "Lion King Magazine"],
  },
  {
    heading: "Help",
    links: [
      "Self Service",
      "Security Centre",
      "FAQ",
      "Contact Us",
      "ATM/Branch Locator",
      "Whistleblowing",
    ],
  },
];

const socialLinks = [
  { label: "Facebook", icon: Facebook, href: "#" },
  { label: "Twitter / X", icon: X, href: "#" }, // ← changed
  { label: "Instagram", icon: Instagram, href: "#" },
  { label: "YouTube", icon: Youtube, href: "#" },
  { label: "LinkedIn", icon: Linkedin, href: "#" },
];
</script>

<template>
  <!-- Header section -->
  <header class="uba-header">
    <nav class="max-w-350 mx-auto px-5 h-20 flex items-center gap-1">
      <!-- Home Icon -->
      <a
        href="/"
        aria-label="Home"
        class="flex items-center text-[#cccccc] hover:text-white px-2.5 py-1.5 rounded transition-colors shrink-0"
      >
        <Home :size="18" />
      </a>

      <!-- Nav Items -->
      <ul class="flex items-center flex-1 list-none m-0 p-0">
        <li v-for="item in navItems" :key="item.label" class="relative">
          <a
            href="#"
            class="flex items-center gap-1 text-[#cccccc] hover:text-white hover:bg-white/5 no-underline text-[18px] font-normal px-3 transition-all"
          >
            {{ item.label }}
          </a>
        </li>
      </ul>

      <!-- Right Side -->
      <div class="flex items-center gap-1.5 ml-auto shrink-0">
        <Button
          variant="ghost"
          size="icon"
          aria-label="Search"
          class="text-[#cccccc] hover:text-white hover:bg-white/10"
        >
          <Search :size="17" />
        </Button>

        <Button
          variant="ghost"
          size="icon"
          aria-label="Language"
          class="text-[#cccccc] hover:text-white hover:bg-white/10"
        >
          <Globe :size="17" />
        </Button>
        <Button
          variant="outline"
          class="ml-1.5 h-10 border-[#888] text-[#eee] bg-transparent hover:border-red-600 hover:bg-red-600 hover:text-white text-[18px] flex items-center gap-2 px-4"
          @click="handleLogin"
        >
          <Lock :size="14" />
          Internet Banking
        </Button>

        <!-- UBA Logo -->
        <RouterLink
          to="/"
          aria-label="UBA Home"
          class="flex flex-col items-end no-underline ml-3 pl-3.5 border-l border-[#333] leading-none"
        >
          <div class="flex items-center gap-1">
            <span class="text-[30px] font-medium text-red-600 italic">UBA</span>
          </div>
          <span class="text-[9px] text-red-500 mt-0.5"
            >United Bank for Africa</span
          >
        </RouterLink>
      </div>
    </nav>
  </header>

  <!-- Hero section -->
  <section class="bg-black px-5 py-20">
    <div class="max-w-350 mx-auto">
      <!-- Heading -->
      <h1 class="text-white font-bold text-5xl leading-tight max-w-lg mb-6">
        Online banking that's easy and efficient
      </h1>

      <!-- Description -->
      <p class="text-white text-base max-w-sm mb-8 leading-relaxed">
        From fund transfers to bill payments, our online platform offers a range
        of features designed to make banking simple, fast, and accessible.
      </p>

      <!-- Buttons -->
      <div class="flex items-center gap-3">
        <Button
          class="bg-red-600 hover:bg-red-700 text-white px-6 py-2 text-sm font-semibold"
          @click="handleLogin"
        >
          Login
        </Button>
        <Button
          variant="outline"
          class="bg-transparent border-white text-white hover:bg-white hover:text-black px-6 py-2 text-sm font-semibold"
        >
          <RouterLink to="/auth/register">Open an account</RouterLink>
        </Button>
      </div>
    </div>
  </section>

  <!-- Footer section -->
  <footer class="bg-[#1a1a1a] border-t border-[#2a2a2a] px-5 py-12">
    <div class="max-w-350 mx-auto">
      <!-- Links Grid -->
      <div class="grid grid-cols-6 gap-8">
        <!-- Footer Columns -->
        <div v-for="col in footerLinks" :key="col.heading">
          <h4 class="text-white font-semibold text-sm mb-4">
            {{ col.heading }}
          </h4>
          <ul class="space-y-3 list-none p-0 m-0">
            <li v-for="link in col.links" :key="link">
              <a
                href="#"
                class="text-[#999] hover:text-white text-sm transition-colors no-underline"
              >
                {{ link }}
              </a>
            </li>
          </ul>
        </div>

        <!-- Connect Column -->
        <div>
          <h4 class="text-white font-semibold text-sm mb-4">Connect</h4>
          <ul class="space-y-3 list-none p-0 m-0">
            <li v-for="social in socialLinks" :key="social.label">
              <a
                :href="social.href"
                class="flex items-center gap-2 text-[#999] hover:text-white text-sm transition-colors no-underline"
              >
                <component :is="social.icon" :size="16" />
                {{ social.label }}
              </a>
            </li>
          </ul>
        </div>
      </div>

      <!-- Bottom Bar -->
      <div
        class="border-t border-[#2a2a2a] mt-10 pt-6 flex items-center justify-between"
      >
        <p class="text-[#666] text-xs">
          © {{ new Date().getFullYear() }} United Bank for Africa. All rights
          reserved.
        </p>
        <div class="flex gap-5">
          <a
            href="#"
            class="text-[#666] hover:text-white text-xs transition-colors no-underline"
            >Privacy Policy</a
          >
          <a
            href="#"
            class="text-[#666] hover:text-white text-xs transition-colors no-underline"
            >Terms of Use</a
          >
          <a
            href="#"
            class="text-[#666] hover:text-white text-xs transition-colors no-underline"
            >Cookie Policy</a
          >
        </div>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.uba-header {
  background-color: #111111;
  width: 100%;
  position: sticky;
  top: 0;
  z-index: 1000;
  border-bottom: 1px solid #2a2a2a;
}
</style>
