import router from '@/router'
import { instance } from '@/util/http-commons'
import { httpStatus } from '@/util/http-status'
import Swal from 'sweetalert2'
/**
 * 로그인 메소드
 * @param {String} userId : 사용자 아이디
 * @param {String} password: 비밀번호
 * @param {Function} success
 * @param {Function} fail
 */
const login = async (userId, password, success, fail) => {
    await instance.post('/user/login', {userId: userId, password: password}, {withCredentials: true})
    .then(success)
    .catch(fail)
}

/**
 * 회원가입 메소드
 * @param {Object} user : 회원가입 form에 입력한 사용자 정보
 * @param {Function} success
 * @param {Funciton} fail
 */
const signUp = async (user, success, fail) => {
    await instance.post('/user/signup', user)
    .then(success)
    .catch(fail)
}

/**
 * 로그아웃 메소드
 * 현재 존재하는 사용자 정보를 제거
 */
const logout = async (success, fail) => {
    await instance.delete("/user/logout", { withCredentials : true })
    .then((success))
    .catch((fail))
}

/**
 * ID 중복 검사 메소드
 * 현재 입력한 ID가 이미 존재하는 ID인지 확인
 * @param {String} userId : 사용자가 입력한 ID
 */
const checkUserID = async (userId, success, fail) => {
    await instance.get(`/user/checkID/${userId}`)
    .then(success)
    .catch(fail);
}

/**
 * 현재 사용자의 마이페이지로 이동 
 * 사용자 정보 및 입력한 item 목록 보여주기 
 */
const myPage = async (success, fail) => {
    await instance.get(`/user/myPage`)
    .then(success)
    .catch(fail);
}

/**
 * 현 사용자의 뱃지 리스트 정보 얻어오기
 */
const getItemList = async (userId, success, fail) => {
    await instance.get(`/user/items`, { params : {'userId': userId}})
    .then(success)
    .catch(fail);
}


instance.interceptors.request.use((config) => {
    return config;
}, (error) => {
    return Promise.reject(error);
})

// 토큰 재발급 중 새로운 요청이 들어오는 문제를 방지한다.
let isTokenRefreshing = false;

// response: 토큰에 문제가 없는 경우 정상적인 response를 받는다.
// error: token의 문제로 error가 발생하는 경우 재발급을 위한 절차를 진행
instance.interceptors.response.use((response) => {
    return response;
}, async (error) => {
    const { config, response } = error;
    if (response.status === httpStatus.UNAUTHORIZED) {
        console.log("UNAUTHORIZED");
        const previousRequest = config; // 이전 요청 정보를 기록

        if (!isTokenRefreshing) {
            isTokenRefreshing = true; // true로 하여 토큰 발급 중 새롭게 들어오는 요청을 blocking
            console.log("재발급");
            return await instance.get('/user/refresh', { withCredentials: true })
            .then((response) => { // 새 요청을 통해 header를 새롭게 setting
                const newAccessToken = response.headers.authorization;
                instance.defaults.headers.common['Authorization'] = newAccessToken;
                previousRequest.headers.authorization = newAccessToken;

                isTokenRefreshing = false;
                console.log(previousRequest);
                return instance(previousRequest) // 새로운 header를 반환
            })
            .catch((error) => {
                Swal.fire({ title:'Error!', text: '로그인이 필요합니다!', icon: 'error', position:'center',
                customClass : { container: 'custom-swal-container' }
              })

              router.replace({name: 'main'});
            }) 
        }
    }
})

export const userApi = {
    login, logout, signUp, checkUserID, myPage, getItemList
}