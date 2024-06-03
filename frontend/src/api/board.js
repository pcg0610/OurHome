import { instance } from "@/util/http-commons"

/**
 * 게시글 등록 메소드
 * @param {Object} article 
 * @param {Function} success 
 * @param {Function} fail 
 */
const registArticle = async (article, success, fail) => {
    await instance.post('/article/write', article)
            .then(success)
            .catch(fail)
}

/**
 * 게시글 조회 메소드
 * @param {BigInt} homeId
 * @param {Function} success
 * @param {Function} fail
 */
const getArticleListByHome = async(homeId, success, fail) => {
    await instance.get('/article/home/list', { params: {
        'homeId' : homeId
    }})
    .then(success)
    .catch(fail)
}

const getArticleListById = async(userId, success, fail) => {
    await instance.get('/article/id/list', { params: { 'userId' : userId}})
    .then(success)
    .catch(fail)
}

const removeArticle = async(article, success, fail) => {
    await instance.delete('/article/remove', { data : {
        'id': article.id,
        'userId' : article.userId,
        'homeId': article.homeId,
        'title': article.title,
        'content': article.content,
        'registeredDate' : article.registeredDate
    }})
    .then(success)
    .catch(fail);
}

const updateArticle = async(article, success, fail) => {
    console.log(article);
    console.log(article.id);
    console.log(article.userId);
    await instance.put('/article/update', {
        'id': article.id,
        'userId': article.userId,
        'homeId': article.homeId,
        'title': article.title,
        'content': article.content,
        'registeredDate': new Date()}
    )
    .then(success)
    .catch(fail)
}

export const api =  {
    registArticle, getArticleListByHome, getArticleListById, removeArticle, updateArticle
}