<script setup>
import { useUserStore } from '@/stores/userStore'
import { useAppStore } from '@/stores/appStore';
import LoginModalComponent from '../modal/LoginModalComponent.vue';
import SignupModalComponent from '../modal/SignupModalComponent.vue';

const userStore = useUserStore();
const appStore = useAppStore();

// 로그아웃 시 기존 사용자 정보 제거
// 향후 jwt 토큰을 제거하는 것으로 변경
function logout() {
  userStore.logout();
}
</script>

<template>

    <v-app-bar-title>
      <v-btn :to="{name: 'main'}">
        Our Home
      </v-btn>
    </v-app-bar-title>

    <div v-if="userStore.user.id !== ''">
      <span class="font-weight-black">{{ userStore.user.userId }}님 환영합니다!</span>
    </div>
      <!-- 추후 jwt 토큰 존재 여부로 change -->
      <v-btn v-if="userStore.user.id !== ''" @click="logout">
          logout
      </v-btn>
    
    <v-btn :to="{name: 'mypage-info'}" v-if="userStore.user.id !== ''">
        MyPage
    </v-btn>

    <!-- 로그인을 위한 버튼 -->
    <!-- 추후 jwt 토큰 존재 여부로 change -->
    <v-btn @click="appStore.showLogin = true" v-if="userStore.user.id === ''">
        login
    </v-btn>

    <!-- 회원 가입을 위한 버튼 -->
    <!-- 추후 jwt 토큰 존재 여부로 change -->
    <v-btn @click="appStore.showSignup = true" v-if="userStore.user.id ===''">
      SignUp
    </v-btn>

    <LoginModalComponent v-model="appStore.showLogin"/>
    <SignupModalComponent v-model="appStore.showSignup"/>
</template>

<style scoped>

</style>