<script setup>
import { onMounted, ref} from 'vue'
import OpenAI from 'openai';
import { useMapStore } from '@/stores/kakaoMapStore';

const mapStore = useMapStore()

const openai = new OpenAI({
  apiKey: import.meta.env.VITE_OPENAI_API_KEY,
  dangerouslyAllowBrowser: true
})

const searchText = ref('')

const addressList = ref([])

onMounted(() => {
  getAddress();
})

const sendRequestToGPT = async (location) => {
  const chatCompletion = await openai.chat.completions.create({
    messages: [
      {
        role: 'system',
        content: '너는 JSON 형태로 {lat, lng} 해당 지역의 좌표를 반환하는 시스템이야'
      },
      {
        role: 'user',
        content: `${location} 좌표를 JSON 형태로 알려줘`
      },
    ],
    model: 'gpt-3.5-turbo-0125',
    response_format: {type: "json_object"}
  })

  let coord = JSON.parse(chatCompletion.choices[0].message.content)
  return coord

}

const getAddress = () => {
  const url = "https://grpc-proxy-server-mkvo6j4wsq-du.a.run.app/v1/regcodes?regcode_pattern=*00&is_ignore_zero=true";
  fetch(`${url}`)
    .then((response) => response.json())
    .then((data) => {
      const juso = data.regcodes
      addressList.value = juso
    }
  );
}

const search = async () => {
  let coord = await sendRequestToGPT(searchText.value);

  mapStore.panTo(coord)
  mapStore.mapMoveEventHandler()
}
</script>

<template>
  <v-col>
    <v-autocomplete
      v-model="searchText"
      prepend-inner-icon="mdi-magnify"
      density="compact"
      label="지역을 입력하세요"
      no-data-text="없는 지역입니다."
      :items="addressList"
      variant="outlined"
      hide-details
      hide-selected
      :menu="false"
      item-title="name"
      item-value="name"
      @click:prepend-inner="search"
      rounded
    ></v-autocomplete>
  </v-col>
</template>

<style scoped>

</style>