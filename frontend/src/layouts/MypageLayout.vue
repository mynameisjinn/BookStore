<script setup>

import UserSidebar from "../components/UserSidebar.vue";
</script>

<template>
    <div class="flex gap-4 mx-auto my-10 max-w-screen-lg ">
      <div class="flex-[1]">
        <UserSidebar />
      </div>
      <div class="flex-[3]">
        <RouterView />
      </div>
    </div>
</template>
