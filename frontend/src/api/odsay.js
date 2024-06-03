import { instance } from "@/util/http-commons"

/**
 * 
 * @param {Number} startLat 시작위치 위도
 * @param {Number} startLng 시작위치 경도
 * @param {Number} endLat 종료위치 위도
 * @param {Number} endLng 종료위치 경도
 * @param {*} success 
 * @param {*} fail 
 */
const getPubTransPathInfo = (startLat, startLng, endLat, endLng, success, fail) => {
    let config = {params: {apiKey: 'C/LCgVZkDnYNTinrZJtINA', 
        SX: startLat, SY: startLng,
        EX: endLat, EY: endLng,
        OPT: 0,
        lang: 0}}
    instance.get('https://api.odsay.com/v1/api/searchPubTransPathT', config)
            .then(success)
            .catch(fail)
}

export const api =  {
    getPubTransPathInfo
}