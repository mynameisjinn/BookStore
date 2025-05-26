<script setup>
import UserSidebar from "../../components/UserSidebar.vue";
import {useAuthStore2} from "../../stores/auth-with-refresh.js";
import {computed} from "vue";


</script>

<template>
  <div class="flex gap-4 mx-auto my-10 max-w-screen-lg ">
    <div class="flex-[1]">
      <UserSidebar />
    </div>
    <div class="flex-[3]">
      <h1>Login 성공!!</h1>
      <h1>My Page</h1>
<!--      <p>{{ username }}</p>-->
    </div>
  </div>
</template>
