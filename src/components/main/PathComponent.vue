<script setup>
import { useAppStore } from '@/stores/appStore';
import { useODSayStore } from '@/stores/odsayStore';
import { ref } from 'vue';
const odsayStore = useODSayStore()
const appStore = useAppStore()

const colorMap = ref([null,
  'light-blue-lighten-3',
  'teal-lighten-3',
  'lime-lighten-3',
  'green-lighten-3',
  'orange-lighten-3',
  'blue-grey-lighten-3',
  'grey-lighten-1'])

const minuteToTime = (minute) => {
  if (minute > 60) {
    return (minute / 60).toFixed(0) + '시간 ' + (minute % 60) + '분'
  }
  return minute + '분'
}

const close = () => {
  appStore.pathShow = false;
}

const walkerPrint = (index, last) => {
  if (index === 0) {
    return '시작지점'
  } else if (index === last) {
    return '도착지점'
  } else {
    return '환승'
  }
}

const distanceFormatter = (distance) => {
  if (distance > 1000) {
    return (distance / 1000).toFixed(1) + 'km'
  } else {
    return distance + 'm'
  }
}

</script>

<template>
  <v-container>
    <v-row class = "ma-0 pa-0">
      <div>최적 추천 경로</div>
      <v-btn icon="mdi-close" density="compact" variant="plain" style="margin-left: auto;" @click="close"></v-btn>
    </v-row>
    <v-row>
      <v-col>
        총 소요시간: {{ minuteToTime(odsayStore.path.info.totalTime) }}
      </v-col>
      <v-col>
        총 비용: {{ odsayStore.path.info.payment }}원
      </v-col>
    </v-row>
  </v-container>
  <v-timeline class="z-index" align="start" side="end">
    <v-timeline-item v-for="(subPath, index) in odsayStore.path.subPath" :key="index"
    :dot-color="colorMap[subPath.trafficType]" size="small">
      <div class="d-flex">
        <strong class="me-4" v-if="subPath.sectionTime > 60">{{ minuteToTime(subPath.sectionTime) }}</strong>
        <div>
          <strong v-if="subPath.trafficType === odsayStore.trafficType.walk">{{ odsayStore.trafficTypeToString[subPath.trafficType] }}({{ walkerPrint(index, odsayStore.path.subPath.length) }})</strong>
          <strong v-if="subPath.trafficType !== odsayStore.trafficType.walk">{{ odsayStore.trafficTypeToString[subPath.trafficType] }}({{ subPath.startName }} => {{ subPath.endName }})</strong>
          
          <div class="text-caption">
            {{ distanceFormatter(subPath.distance) }}
          </div>
        </div>
      </div>
    </v-timeline-item>
  </v-timeline>
</template>

<style scoped>

* {
    z-index: 1;
}

.transitPath {
    position: fixed;
    left: 864px;
}

</style>