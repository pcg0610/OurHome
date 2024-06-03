<script setup>
    import { ref, computed } from 'vue';
    import { VDateInput } from 'vuetify/labs/VDateInput'
    import UserInfoComponent from './UserInfoComponent.vue';
    import { useUserStore } from '@/stores/userStore';
    import { useAppStore } from '@/stores/appStore';
    import { httpStatus } from '@/util/http-status';
    import { userApi } from '@/api/user';

    const userStore = useUserStore();
    const appStore = useAppStore();

    // window 전환을 위한 index 0: 기본 정보 입력, 1: 뱃지 입력 폼 2: 회원가입 완료 폼
    const step = ref(0)

    // 각 window의 제목
    const title = ref(['기본 정보 입력', '자신을 알릴 수 있는 정보를 입력해 주세요']);

    // 비밀번호 toggle
    const visible = ref(false)
    const visibleCheck = ref(false);

    const password = ref('');
    const passwordCheck = ref('');

    // 전화번호
    const start = ref('');
    const mid = ref('');
    const end = ref('');

    // 회원 가입을 위한 객체
    const newUser = ref({
      id:0,
      userId:'',
      password:'',
      phoneNumber: '',
      name:'',
      gender:'M',
      birth: null,
      image:'',
      personality: userStore.items
    })

    // 모든 정보를 다 입력한 경우에 한하여 회원가입 정보를 저장한다.
    // 회원가입 요청 메소드
    async function signUp() {
      makePhoneNumber();
      await userStore.signUp(newUser.value);

      if (userStore.code === httpStatus.CREATED) {
        console.log("회원가입");
        clearInfo();
        appStore.showSignup = false;
      }
    }

    // modal 외부를 클릭하여 나가는 경우 데이터를 저장하지 않는다.
    function notSave() {
      step.value = 0;
      appStore.showSignup = false;
      clearInfo();
    }

    // 입력한 내용 초기화
    function clearInfo() {
      newUser.value.userId = '';
      newUser.value.name = '';
      newUser.value.phoneNumber = '';
      newUser.value.password = '';
      newUser.value.birth = null;
      newUser.value.gender=''
      checkMessage.value = '';
      alertType.value = '';
      password.value = '';
      passwordCheck.value = '';

      mid.value = '';
      end.value = '';

      userStore.items = [];
    }

    const checkMessage = ref('');
    const alertType = ref('');
    
    const makePhoneNumber = () => {
      newUser.value.phoneNumber = '010' + '-' + mid.value + '-' + end.value;
    }

    const checkDuplication = async() => {
      // 공백을 포함하는 경우 확인
      if (newUser.value.userId.charAt(newUser.value.userId.length - 1) === ' ' || newUser.value.userId.length === 0) {
        checkMessage.value = '공백을 포함할 수 없습니다.'
        alertType.value = 'text-red';
        return;
      }

      await userApi.checkUserID(newUser.value.userId, 
        (response) => {
          checkMessage.value = '사용할 수 있는 ID 입니다.'
          alertType.value = 'text-green';
        },
        (error) => {
          checkMessage.value = '사용할 수 없는 ID 입니다.'
          alertType.value = 'text-red';
        }
      )
    }

    // 동일한 비밀번호 입력 여부 판단
    const isSamePassword = computed( () => {
      return (newUser.value.password === passwordCheck.value);
    })

    // 회원가입 입력 form 유효성 검사 메소드
    const checkValidation = computed( () => {
      return (alertType.value !== 'text-green' || newUser.value.name === '' || isSamePassword === false || newUser.value.birth === null)
    })

</script>

<template>
  <v-dialog max-width="500" v-model="appStore.showSignup" @click:outside="notSave()">
    <v-card prepend-icon="mdi-account" title="회원 가입">
      <v-card-title
        class="ms-3 text-h6 font-weight-regular justify-space-between"
      >
      <span> {{ title[step] }} </span>
      </v-card-title>

      <v-window v-model="step">
        <v-window-item :value="0">
          <v-card-text>
            <v-text-field
              class="mx-3"
              density="compact"
              placeholder="아이디 입력"
              prepend-inner-icon="mdi-pencil-plus-outline"
              variant="outlined"
              v-model="newUser.userId"
              @keyup="checkDuplication"
              hide-details="auto"
            >
            </v-text-field>

            <div v-if="checkMessage !== ''">
              <p class="mx-3" :class="alertType">{{ checkMessage }}</p>
            </div>

            <div style="min-height:20px" v-if="checkMessage === ''">

            </div>

            <v-text-field
              class="mx-3 mt-2"
              density="compact"
              placeholder="이름 입력"
              prepend-inner-icon="mdi-pencil-plus-outline"
              variant="outlined"
              v-model="newUser.name"
              hide-details="auto"
            ></v-text-field>

            <div class="d-flex flex-row mt-3">
              <v-text-field
                class="mx-3 mt-2 flex-grow-0 align-self-stretch"
                width="270"
                density="compact"
                placeholder="전화번호"
                value="010"
                prepend-inner-icon="mdi-phone-classic"
                variant="outlined"
                hide-details="auto"
                type="number"
                hide-spin-buttons
                read-only
              ></v-text-field>

              <v-text-field
                class="mx-3 mt-2 flex-grow-0 align-self-stretch"
                width="270"
                density="compact"
                variant="outlined"
                placeholder="전화번호"
                hide-details="auto"
                type="number"
                hide-spin-buttons
                v-model="mid"
                :maxlength="4"
              ></v-text-field>

              <v-text-field
                class="mx-3 mt-2 flex-grow-0 align-self-stretch"
                width="270"
                density="compact"
                variant="outlined"
                placeholder="전화번호"
                hide-details="auto"
                type="number"
                hide-spin-buttons
                v-model="end"
                :maxlength="4"
              ></v-text-field>
            </div>

            <v-text-field
              class="mx-3 mt-6"
              :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
              :type="visible ? 'text' : 'password'"
              density="compact"
              placeholder="비밀번호 입력"
              prepend-inner-icon="mdi-lock-outline"
              variant="outlined"
              @click:append-inner="visible = !visible"
              v-model="newUser.password"
              hide-details="auto"
            ></v-text-field>

            <v-text-field
              class="mx-3 mt-6"
              :append-inner-icon="visibleCheck ? 'mdi-eye-off' : 'mdi-eye'"
              :type="visibleCheck ? 'text' : 'password'"
              density="compact"
              placeholder="비밀번호 확인"
              prepend-inner-icon="mdi-lock-outline"
              variant="outlined"
              @click:append-inner="visibleCheck = !visibleCheck"
              v-model="passwordCheck"
              hide-details="auto"
            ></v-text-field>

            <div v-if="isSamePassword === false">
              <p class="mx-3 text-red">비밀번호가 일치하지 않습니다.</p>
            </div>

            <div style="min-height:20px" v-else>
                
            </div>
            <v-radio-group inline v-model="newUser.gender">
              <template v-slot:label>
                <div class="text-h6 font-weight-black">성별</div>
              </template>
              <v-radio color="primary" label="남" value="M"></v-radio>
              <v-radio color="primary" label="여" value="F"></v-radio>
            </v-radio-group>

            <v-card-text>
              <div class="text-h6 font-weight-black">생년월일</div>
            </v-card-text>

            <v-row dense class="mx-3">
              <v-col>
                <v-date-input
                  label="Select a date"
                  prepend-icon=""
                  prepend-inner-icon="$calendar"
                  variant="solo"
                  v-model="newUser.birth"
                ></v-date-input>
              </v-col>
            </v-row>
          </v-card-text>
        </v-window-item>

        <v-window-item :value="1">
          <UserInfoComponent class="mb-5"/>
        </v-window-item>

        <v-window-item :value="2">
          <div class="pa-4 text-center">
            <v-img
              class="mb-4"
              height="128"
              src="https://cdn.vuetifyjs.com/images/logos/v.svg"
              contain
            ></v-img>
            <h3 class="text-h6 font-weight-light mb-2">회원가입 완료!</h3>
            <span class="text-caption text-grey">Thanks for signing up!</span>
          </div>
        </v-window-item>
      </v-window>

      <v-divider></v-divider>

      <v-card-actions>
        <v-btn v-if="step > 0 && step != 2" variant="text" @click="step--">
          Back
        </v-btn>
        <v-spacer></v-spacer>
        <v-btn :disabled = checkValidation v-if="step < 2" color="primary" variant="flat" @click="step++">
          Next
        </v-btn>
        <v-btn v-if="step == 2" color="primary" variant="flat" @click="signUp()">
          Close
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>


</style>