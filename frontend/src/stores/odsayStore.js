import { defineStore } from "pinia";
import { ref } from "vue";

export const useODSayStore = defineStore('odsay-store', () => {

    /**
     * state
     */

    const start = ref(null)

    const end = ref(null)

    const path = ref({})

    const trafficType = {
        subway: 1,
        bus: 2,
        walk: 3,
        train: 4,
        gosokBus: 5,
        siueBus: 6,
        airPlane: 7
    }

    const trafficTypeToString = [null, '지하철', '시내버스', '도보', '기차', '고속버스', '시외버스', '항공기']

    /**
     * action
     */

    const getPubTransPathInfo = async () => {
        if (!start.value || !end.value) {
            return;
        }

        let sx = start.value.lng
        let sy = start.value.lat
        let ex = end.value.lng
        let ey = end.value.lat
        var xhr = new XMLHttpRequest();
        var url = `https://api.odsay.com/v1/api/searchPubTransPathT?SX=${sx}&SY=${sy}&EX=${ex}&EY=${ey}&apiKey=${import.meta.env.VITE_ODSAY_API_KEY}`;
        xhr.open("GET", url, true);
        xhr.send();
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4 && xhr.status == 200) {
                console.log(JSON.parse(xhr.responseText))

                let result = JSON.parse(xhr.responseText)

                path.value = result.result.path[0];
            }
        }
    }

    return {
        start, end, path, trafficType, trafficTypeToString, getPubTransPathInfo
    }
})