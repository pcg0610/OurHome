import { instance } from "@/util/http-commons"

// /**
//  * 매물 중 이름이 검색 내용을 포함하는 매물들을 요청하는 메서드
//  * @param {String} query 검색 내용
//  * @param {Function} success 
//  * @param {Function} fail 
//  */
// const listHomeComboboxItems = async (content, success, fail) => {
//     await instance.get('/home', {params: {content: content}})
//             .then(success)
//             .catch(fail)
// }

const listHome = async (params, success, fail) => {
    await instance.get('/home/list', params)
            .then(success)
            .catch(fail)
}

/**
 * 사용자의 매물 즐겨찾기 목록을 요청하는 메서드
 * @param {BigInt} userId 사용자 고유 번호
 * @param {Function} success
 * @param {Function} fail
 */
const favoriteHomes = async (userId, success, fail) => {
    await instance.get('/home/favorites', { params: {
        'userId' : userId
    }})
    .then(success)
    .catch(fail);

    //await instance.get('/home/favorites', {'userId': userId})
    //    .then(success)
    //    .catch(fail)
}

/**
 * 집 하나의 상세 정보를 조회하는 메서드
 * @param {BigInt} homeId 집 고유 번호
 * @param {Function} success 
 * @param {Function} fail 
 */
const detailHome = async (homeId, success, fail) => {
    await instance.get(`/home/${homeId}`)
        .then(success)
        .catch(fail)
}

/**
 * 즐겨찾기 목록에 집을 추가하는 메소드
 * @param {BigInt} userId 
 * @param {BigInt} homeId 
 * @param {Function} success 
 * @param {Function} fail 
 */
const addFavorite = async(userId, homeId, success, fail) => {
    console.log(userId + " " + homeId);
    await instance.post(`home/insert`, {userId: userId, homeId: homeId})
    .then(success)
    .catch(fail);
}

/**
 * 즐겨찾기 목록에 있는 집을 제거하는 메소드
 * @param {BigInt} userId
 * @param {BigInt} homeId
 * @param {Function} success
 * @param {Function} fail
 */
const deleteFavorite = async(userId, homeId, success, fail) => {
    console.log(userId + " " + homeId);
    await instance.delete(`home/delete`, { data : {userId : userId, homeId: homeId}})
    .then(success)
    .catch(fail)
}

export const api =  {
    favoriteHomes,
    detailHome,
    listHome,
    addFavorite,
    deleteFavorite
}