<script setup>
import { computed } from 'vue';
import { useHomeStore } from '@/stores/homeStore';
import { useAppStore } from '@/stores/appStore';
import { useUserStore } from '@/stores/userStore';
import Swal from 'sweetalert2';
import { useBoardStore } from '@/stores/boardStore';
import { useMapStore } from '@/stores/kakaoMapStore';

const mapStore = useMapStore()
const homeStore = useHomeStore()

const appStore = useAppStore()
const userStore = useUserStore();
const boardStore = useBoardStore();

const props = defineProps({
    item: Object,
})

const icon = computed(() => {
    if (props.item.favorite) {
        return "mdi-star"
    } else {
        return "mdi-star-outline"
    }
})

const toggleFavorite = (event) => {
    // favorite뿐만 아니라 cardClick까지 실행되는 것을 막기 위한 코드(함수 내 최상단에 둘 것)
    event.stopPropagation()

    if (userStore.user.id === '') {
        Swal.fire({ title:'Error!', text: '로그인이 필요합니다!', icon: 'error', position:'center',
          customClass : { container: 'custom-swal-container' }
        })
        return;
    }
    
    if (props.item.favorite) {
        homeStore.removeItemInFavoriteList(userStore.user.id, props.item.id)
    } else {
        homeStore.addItemInFavoriteList(userStore.user.id, props.item.id)
    }
}

const cardClick = async () => {
    if (!homeStore.home || homeStore.home.id !== props.item.id) {
        appStore.detailShow = true
        homeStore.getDetail(props.item.id)
        boardStore.getArticleListByHome(props.item.id);
        window.kakaoMap.setLevel(3)
        mapStore.panTo({lat: props.item.lat, lng: props.item.lng})
    } else {
        appStore.detailShow = !appStore.detailShow
        return;
    }
}
</script>

<template>
    <v-card
    class="borders"
    height="150px"
    hover
    @click="cardClick">
        <v-container fluid height="100%">
            <v-row>
                <v-col
                cols="4">
                    <v-img 
                    height="100%"
                    aspect-ratio="1"
                    :src="!item.images ? null : item.images[0]"
                    cover
                    >
                    </v-img>
                </v-col>
                <v-col
                cols="8">
                    <v-container class="pa-0">
                        <v-row align="center" class="pa-0 ma-0">
                            <v-col cols=10 class="pa-0">
                                <v-card-title class="pa-0 font-weight-bold">
                                    {{ item.name }}
                                </v-card-title>
                            </v-col>
                            <v-col cols=2 class="pa-0 ps-5">
                                <v-btn variant="plain" density="compact" :icon="icon" @click="toggleFavorite"></v-btn>
                            </v-col>
                        </v-row>
                    </v-container>
                    <v-card-text  class="pa-0">
                        주소: {{ item.address }}
                    </v-card-text>
                    <v-card-text  class="pa-0">
                        전세: {{ !item.jeonsae ? 0 : item.jeonsae }} 만원
                    </v-card-text >
                    <v-card-text  class="pa-0">
                        월세: {{ !item.monthlyDeposiot ? 0 : item.monthlyDeposiot }}/{{ item.monthlyPay }}
                    </v-card-text >
                    <v-card-text class="pa-0">
                        면적: {{ Math.round(item.area * 100) / 100 }} m<sup>2</sup>
                    </v-card-text>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
</template>

<style scoped>
.v-card-text {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>