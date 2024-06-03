<script setup>
import DetailTogetherPostListComponent from '@/components/main/detail/DetailPostListComponent.vue';
import UserInputArticleComponent from '@/components/modal/UserInputArticleComponent.vue';
import { useAppStore } from '@/stores/appStore';
import { useHomeStore } from '@/stores/homeStore';
import { useODSayStore } from '@/stores/odsayStore';
import { useUserStore } from '@/stores/userStore';
import Swal from 'sweetalert2';

const appStore = useAppStore()

const odsayStore = useODSayStore()
const homeStore = useHomeStore()
const userStore = useUserStore();

const closeDetail = () => {
    appStore.detailShow = false    
}

const openBoard = () => {
    if (userStore.user.id === '') {
        Swal.fire({ title:'Wrong!', text: '글 작성을 위해서는 로그인이 필요합니다.!', icon: 'info', position:'center',
          customClass : { container: 'custom-swal-container' }
        })
        return;
    }

    appStore.showArticle = true;
}

const setStart = async () => {
    odsayStore.start = {lat: Number(homeStore.home.lat), lng: Number(homeStore.home.lng)}
    await odsayStore.getPubTransPathInfo()
    appStore.pathShow = true
}

</script>

<template>
    <v-row justify="end">
        <v-btn @click="closeDetail" icon="mdi-close" variant="text" density="compact"></v-btn>
    </v-row>
    <v-row>
        <v-carousel height="200px">
            <v-carousel-item v-for="(img, index) in homeStore.home.images"
            :src="img"
            :key="index"
            cover
            eager
            ></v-carousel-item>
        </v-carousel>
    </v-row>
    <v-row>
        <v-col cols="12" class="pa-0 px-1 pt-2 d-flex justify-space-between">
            <h3 class="shorted-text">
                {{ homeStore.home.name }}
            </h3>
            <v-btn variant="tonal" density="compact" @click="setStart">길찾기</v-btn>
        </v-col>
        <v-col cols="12" class="pa-0 px-1">
            <p class="shorted-text my-2"> {{ homeStore.home.address }}</p>
        </v-col>
        <v-table density="compact" class="w-100 border rounded-lg ">
            <tbody>
                <tr>
                    <th scope="row">전세</th>
                    <td>{{ homeStore.home.jeonsae }} 만원</td>
                    <th scope="row">월세</th>
                    <td>{{ homeStore.home.monthlyPay }} 만원</td>
                </tr>
                <tr>
                    <th scope="row">면적</th>
                    <td>{{ Math.round(homeStore.home.area * 100) / 100}}m<sup>2</sup></td>
                    <th scope="row">건축 년도</th>
                    <td>{{ homeStore.home.buildYear }} 년</td>
                </tr>
                <tr>
                    <th scope="row">연락처</th>
                    <td colspan="3">{{ homeStore.home.phone }}</td>
                </tr>
            </tbody>
        </v-table>
    </v-row>

    <!-- 모집 글 작성을 위한 modal을 띄우기 위해 + 버튼 추가 -->
    <v-row>
        <v-col cols="8">
            <h2 class="w-100 border-b-md mb-2">동거인 모집글</h2>
        </v-col>
        <v-col cols="auto" class="ms-auto">
            <v-btn density="compact" icon="mdi-plus" variant="text" @click="openBoard"></v-btn>
        </v-col>
        <UserInputArticleComponent v-model="appStore.showArticle"/>
        <DetailTogetherPostListComponent />
    </v-row>
</template>

<style scoped>
* {
    padding: 0;
    margin:0;
}

.shorted-text {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

/* table {
    border-radius: 5%;
    border: 1px solid black;
}

th, td {
    border: 1px solid ;
} */

th {
    background-color: #DBDBDB;
}
</style>