<script setup>
    import { ref } from 'vue'
    import { useUserStore } from '@/stores/userStore';
    import { httpStatus } from '@/util/http-status';
    import { useAppStore } from '@/stores/appStore';

    import Swal from 'sweetalert2';

    // 비밀번호 공개 여부
    const visiblePassword = ref(false)
    const userStore = useUserStore();
    const appStore = useAppStore();
    
    // 사용자 로그인을 위한 객체
    const loginUser = ref({
      userId: '',
      password: '',
    })

    // 외부를 클릭해서 모달 창을 닫는 경우 입력 정보 초기화
    function close() {
      appStore.showLogin = false;
      clearInfo();
    }

    function clearInfo() {
      loginUser.value.userId = '';
      loginUser.value.password='';
    }

    // 로그인 버튼 클릭 시 입력한 정보를 메인 화면에 전달
    async function login() {
      await userStore.login(loginUser.value.userId, loginUser.value.password);

      if (userStore.code === httpStatus.OK) {
        Swal.fire({
          title: 'Success!', text: '로그인 성공', icon: 'success', position: 'center',
          customClass: { container: 'custom-swal-container' }
        })

        clearInfo();
        appStore.showLogin = false;
      }

      else if (userStore.code === httpStatus.NO_CONTENT) {
        Swal.fire({ title:'Wrong!', text: 'ID / 비밀번호를 확인해 주세요', icon: 'info', position:'top', 
          customClass : { container: 'custom-swal-container' }
        })
      }

      else {
        Swal.fire({ title:'Error!', text: '로그인 실패', icon: 'error', position:'top',
          customClass : { container: 'custom-swal-container' }
        })
      }
    }

</script>

<template>
    <!-- 모달 창 바깥 쪽을 클릭하는 경우 모달창 close -->
    <v-dialog max-width="448" v-model="appStore.showLogin" @click:outside="close">
      <v-card prepend-icon="mdi-account" title="User Profile">
        <v-card-text>
          <v-row dense>
            <v-col>
              <v-text-field
                density="compact"
                placeholder="ID"
                prepend-inner-icon="mdi-pencil-plus-outline"
                variant="outlined"
                v-model="loginUser.userId"
              ></v-text-field>
            </v-col>
          </v-row>

          <!-- 비밀번호 toggle을 통해 입력한 비밀번호 확인 -->
          <v-row dense>
            <v-col>
              <v-text-field
                :append-inner-icon="visiblePassword ? 'mdi-eye-off' : 'mdi-eye'"
                :type="visiblePassword ? 'text' : 'password'"
                density="compact"
                placeholder="Enter your password"
                prepend-inner-icon="mdi-lock-outline"
                variant="outlined"
                @click:append-inner="visiblePassword = !visiblePassword"
                v-model="loginUser.password"
              ></v-text-field>
            </v-col>
          </v-row>

          <v-row dense>
            <v-col>
              <v-btn
                color="blue"
                size="large"
                variant="tonal"
                block
                @click="login"
              >
                Log In
              </v-btn>
            </v-col>
          </v-row>

          <v-card-text class="text-center">
            <a
              class="text-blue text-decoration-none"
              href="#"
              rel="noopener noreferrer"
              target="_blank"
            >
              Sign up now <v-icon icon="mdi-chevron-right"></v-icon>
            </a>
          </v-card-text>
        </v-card-text>
      </v-card>
    </v-dialog>
</template>

<style>
  .custom-swal-container {
    z-index: 5000 !important;
  }
</style>