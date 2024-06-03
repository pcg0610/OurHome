<script setup>
import { onMounted } from 'vue'
import { useMapStore } from '@/stores/kakaoMapStore';

const mapStore = useMapStore()

onMounted(() => {
	if (window.kakao && window.kakao.maps) {
		initMap();
	} else {
		const script = document.createElement('script');
	
		/* global kakao */
		script.onload = () => kakao.maps.load(initMap)
		script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${import.meta.env.VITE_KAKAO_MAP_API_KEY}&libraries=clusterer&autoload=false`
		document.head.appendChild(script)
	}
})

const initMap = () => {
	const container = document.getElementById('map')
	const options = {
		center: new kakao.maps.LatLng(37.5642135, 127.0016985),
		level: 4,
	}

	console.log(kakao.maps)

	window.kakaoMap = new kakao.maps.Map(container, options)

	kakao.maps.event.addListener(window.kakaoMap, 'dragend', mapStore.mapMoveEventHandler);
	kakao.maps.event.addListener(window.kakaoMap, 'click', mapStore.mapClickEventHandler)
	kakao.maps.event.addListener(window.kakaoMap, 'zoom_changed', mapStore.mapZoomEventHandler)

	window.kakaoMapClusterer = new kakao.maps.MarkerClusterer({
		map: window.kakaoMap, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
		averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
		minLevel: 5 // 클러스터 할 최소 지도 레벨 
	});

	mapStore.mapMoveEventHandler()
}



// 마우스 드래그로 지도 이동이 완료되었을 때 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다


</script>

<template>
	<div id="map"></div>
</template>

<style scoped>
#map {
	width: 100%;
	height: 100%;
}
</style>