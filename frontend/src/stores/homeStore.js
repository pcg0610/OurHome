import { defineStore } from "pinia";
import { computed, ref } from "vue";
import { api } from "@/api/home";
import { httpStatus } from "@/util/http-status";

export const useHomeStore = defineStore('home-store', () => {

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

    /**
     * state
     */
    const home = ref({})

    const homeList = ref([])
    
    const favoriteList = ref([])

    const comboboxItems = ref([])

    /**
     * getter
     */


    /**
     * action
     */

    const setHomeList = (items) => {
        homeList.value = items;
    }
    
    /**
     * 매물 중 이름이 검색 내용을 포함하는 매물들을 가져오는 메서드
     * @param {String} content 검색할 내용
     */
    const getHomeComboboxItemsByName = async (content) => {
        await api.listHomeComboboxItems(content,
            (response) => {
                if (response.status === httpStatus.OK) {
                    response.data.forEach((item) => item.type = 'home')
                    comboboxItems.value = response.data
                }
            }),
            (error) => {
                console.error(error)
            }
    }

    const getHomeList = async (params) => {
        await api.listHome({params: params},
        (response) => {
            homeList.value = response.data
            homeList.value.forEach((item) => item.images = [homeImages[(10 * Math.random()).toFixed(0)]])
        },
        (error) => {

        })
    }

    const getDetail = async (homeId) => {
        await api.detailHome(homeId,
            (response) => {
                response.data.images = [homeImages[(10 * Math.random()).toFixed(0)]]
                home.value = response.data;
            },
            (error) => {
                console.error(error)
            }
        )
    }

    const getFavorites = async (userId) => {
        await api.favoriteHomes(userId,
            (response) => {
                if (response.data !== '') {
                    favoriteList.value = response.data;
                }
            },
            (error) => {
                console.error(error)
            }
        )
    }

    const removeItemInFavoriteList = async(userId, homeId) => {
        await api.deleteFavorite(userId, homeId,
            (response) => {
                console.log("삭제 성공");
                console.log(response);

                const item = homeList.value.find((item) => item.id === homeId)
                item.favorite = false
                favoriteList.value = favoriteList.value.filter((item) => item.id !== homeId)
            },
            (error) => {
                console.log("삭제 실패");
                console.log(error);
            }
        )
    }
    
    const addItemInFavoriteList = async(userId, homeId) => {
        await api.addFavorite(userId, homeId, 
            (response) => {
                console.log("등록 성공");
                console.log(response);

                // 성공 시
                const item = homeList.value.find((item) => item.id === homeId)
                item.favorite = true

                console.log(item);
                favoriteList.value.push(item)
            },
            (error) => {
                console.log("등록 실패");
                console.log(error);
            }
        )
    }

    return {
        home, homeList, favoriteList, comboboxItems, setHomeList, getHomeList, getHomeComboboxItemsByName, getDetail, getFavorites, removeItemInFavoriteList, addItemInFavoriteList
    }
})