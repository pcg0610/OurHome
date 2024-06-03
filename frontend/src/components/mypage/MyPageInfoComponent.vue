<script setup>
    import { useAppStore } from '@/stores/appStore';
    import { instance } from '@/util/http-commons';
    import { ref } from 'vue';

    const userInfo = ref({
        id: '',
        name: '',
        userId: '',
        gender: '',
        birth: '',
        items: '',
        phoneNumber: '',
        img: '',
    })

    const appStore = useAppStore();

    instance.get('/user/mypage')
    .then((response) => {
        userInfo.value = response.data;
    })
    .catch((error) => {
    })
</script>

<template>
    <v-container :fluid="true" >
        <v-row>
            <v-col>
                <h2>내 정보</h2>
            </v-col>
        </v-row>
        <v-row class="mt-0 px-3">
            <v-avatar color="surface-variant" rounded="0" size="200">
                <v-img v-if="userInfo.img !== null" src="userStore.user.img" sizes=""></v-img>
                <v-icon v-else icon="mdi-account-circle" :size="200"></v-icon>
            </v-avatar>
            <div class="ms-5">
                <p class="big-font">이름: {{ userInfo.name }}</p>
                <p class="big-font">아이디: {{ userInfo.userId }}</p>
                <p class="big-font">성별: {{ userInfo.gender }}</p>
                <p class="big-font" v-if="userInfo.birth !== ''">생년월일: {{ appStore.changeBirth(userInfo.birth) }}</p>
                <p class="big-font">연락처: {{ userInfo.phoneNumber }}</p>
            </div>
        </v-row>

        <v-row>
            <v-col>
                <h2>나의 성향</h2>
                <div class="d-flex justify-start">
                    <p class="text-center mt-3" v-if="userInfo.items.length === 0">
                        내용이 존재하지 않습니다.
                    </p>

                    <v-chip v-for="item in userInfo.items"
                        class="me-3 mb-2 mt-3" 
                        size="large" 
                        color="primary" 
                        variant="elevated">
                        {{ item }}
                    </v-chip>
                </div>
            </v-col>
        </v-row>
    </v-container>
</template>

<style scoped>
.big-font {
    font-size: larger;
}
</style>