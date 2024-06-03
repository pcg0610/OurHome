import { defineStore } from "pinia";
import { useHomeStore } from "./homeStore";
import { api } from "@/api/home";
import { useAppStore } from "./appStore";
import { httpStatus } from "@/util/http-status";
import { useODSayStore } from "./odsayStore";
import { useUserStore } from "./userStore";

export const useMapStore = defineStore('map-store', () => {

    const homeImages = [
        'https://cdn.pixabay.com/photo/2015/04/24/10/18/studio-737569_1280.jpg',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfuPB7HUx13oP7HHYUHnEAUd-4tMlvzBQQecERm7nuCw&s',
        'https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/19Fu/image/t-AZU7qHDngppH1FQF8V-7dY2vI',
        'https://www.yyg.go.kr/www/citizen_participation/publicity/ybmodule.file/board_www/www_company_pr/1675320106.jpg',
        'https://static.wixstatic.com/media/4dd345_a39849627db944789546477c62c0dfea~mv2.jpg/v1/fill/w_532,h_400,al_c,q_80,usm_0.66_1.00_0.01,enc_auto/4dd345_a39849627db944789546477c62c0dfea~mv2.jpg',
        'https://media.bunjang.co.kr/product/107284186_1_1687623552_w360.jpg',
        'https://i1.daumcdn.net/thumb/C480x270/?fname=https://img1.daumcdn.net/thumb/R750x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbuklRy%2FbtqwExzBUOT%2FpPRb02EtTxfRRe6kd9AZDk%2Fimg.jpg',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjihoxbSpty2cCX5ue_wPIDTA6Z4LvgrYb9smAAjW6JxTwWCKvAWgxlfMjaYYKX9e-0NE&usqp=CAU',
        'https://t1.daumcdn.net/thumb/R920x0/?fname=http://zipdeco.co.kr/upload/2023/05/19/EDITOR_202305191118203430_dP8hq',
        'https://medinterior.com/wp-content/uploads/2022/08/%EC%B6%98%EC%B2%9C-%EC%95%84%ED%8C%8C%ED%8A%B8%EB%A6%AC%EB%AA%A8%EB%8D%B8%EB%A7%81-1-1024x768.jpg',
        'https://ms-housing.kr/data/file/residence_gallery/1893533933_JMZkwRQt_7e007afcf36372f893058c91dcc64b65fed06003.jpg'
    ]    

    const appStore = useAppStore()
    const homeStore = useHomeStore()
    const odsayStore = useODSayStore()
    const userStore = useUserStore()

    /**
     * state
     */

    /* global kakao */

    const markers = []

    let oftenPlaceMarker = undefined
    let startPlaceMarker = undefined


    /**
     * getter
     */

    

    /**
     * action
     */
    const panTo = (coord) => {
        // 이동할 위도 경도 위치를 생성합니다 
        var moveLatLon = new window.kakao.maps.LatLng(coord.lat, coord.lng);
        
        // 지도 중심을 부드럽게 이동시킵니다
        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        window.kakaoMap.panTo(moveLatLon);            
    }

    function mapMoveEventHandler() {
	
        // 지도 중심좌표를 얻어옵니다 
        let bounds = window.kakaoMap.getBounds();

        window.kakaoMapClusterer.clear()

        let params = {minLat: bounds.getSouthWest().getLat(), minLng: bounds.getSouthWest().getLng(),
            maxLat: bounds.getNorthEast().getLat(), maxLng: bounds.getNorthEast().getLng(), matchType: 'AND'
        }
        
        if (userStore.user.id !== '') {
            params.userId = userStore.user.id;
        }
        
        api.listHome(
            {params: params},
            (response) => {
                appStore.itemList.splice(0, appStore.itemList.length)
                
                
                markers.splice(0, markers.length)
                response.data.forEach((item) => item.images = [homeImages[(10 * Math.random()).toFixed(0)]])
                homeStore.homeList = response.data
                appStore.itemList.push(...response.data.slice(0, Math.min(10, response.data.length)));
    
                for (let idx = 0; idx < response.data.length; idx++) {
    
                    let marker = new kakao.maps.Marker({
                        position: new kakao.maps.LatLng(response.data[idx].lat, response.data[idx].lng)
                    })
    
                    markers.push(marker);
                }

                window.kakaoMapClusterer.addMarkers(markers)
            },
            (error) => {
    
            }
        )

    }
    
    function mapClickEventHandler(mouseEvent) {
        if (!oftenPlaceMarker) {
            oftenPlaceMarker = new kakao.maps.Marker({
                image: new kakao.maps.MarkerImage("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png", new kakao.maps.Size(24, 35))
            })
        }
    
        let latLng = mouseEvent.latLng;
    
        oftenPlaceMarker.setPosition(latLng);
    
        oftenPlaceMarker.setMap(window.kakaoMap);
    
        odsayStore.end = {lat: latLng.getLat(), lng: latLng.getLng()}
    }

    function mapZoomEventHandler() {
        let level = window.kakaoMap.getLevel();

        if (level > 8) {
            window.kakaoMap.setLevel(8)
        }
    }

    function setStartMarker(lat, lng) {
        let coord = new kakao.maps.LatLng(lat, lng)

        startPlaceMarker = new kakao.maps.Marker({

        })
    }
    

    return {
       panTo, mapClickEventHandler, mapMoveEventHandler, mapZoomEventHandler
    }
})