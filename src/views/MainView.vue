<script setup>
import KakaoMap from '@/components/main/KakaoMap.vue';
import SideComponent from '@/components/main/side/SideComponent.vue';
import DetailComponent from '@/components/main/detail/DetailComponent.vue'
import { useAppStore } from '@/stores/appStore';
import PathComponent from '@/components/main/PathComponent.vue'
import { useODSayStore } from '@/stores/odsayStore';

const appStore = useAppStore()
const odsayStore = useODSayStore()

</script>

<template>

    <v-row class="ma-0 h-100">
        <v-col cols="3" class="pa-0">
            <v-card class="pa-1 h-100">
                <SideComponent />
            </v-card>
        </v-col>
        <v-col cols="9" class="pa-0">
            <KakaoMap />
        </v-col>
        <v-col class="pa-0" id="detail-component">
            <v-card class="pa-1 h-100 z-index" v-if="appStore.detailShow">
                <v-container class="h-100 pa-3">
                    <DetailComponent/>
                </v-container>
            </v-card>
        </v-col>
        <v-col id="trainsit-path">
            <v-card class="z-index" v-if="appStore.pathShow && odsayStore.path">
                <PathComponent />
            </v-card>
        </v-col>
    </v-row>

</template>

<style scoped>
* {
    margin:0;
    padding:0;
}

#detail-component {
    position:absolute;
    left: calc(1920px / 4);
    width: calc(1920px / 5);
    height: calc(100% - 64px);
}

.z-index {
    z-index:1;
}

#trainsit-path {
    position: absolute;
    left: 853px;
    width: 500px;
    top: 54px;
}
</style>