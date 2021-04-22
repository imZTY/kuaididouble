import request from '@/utils/request'

// 分页获取
export function pageListUnfinish(params) {
    return request({
        url: '/kdd/certificate/pageListUnfinish',
        method: 'get',
        params
    })
}

// 获取详情
export function getDetail(params) {
    return request({
        url: '/kdd/certificate/getDetail',
        method: 'get',
        params
    })
}

// 修改角色
export function setRole(params) {
    return request({
        url: '/kdd/certificate/dealCheck',
        method: 'post',
        params
    })
}

// 上传图片
export function uploadFile(data) {
    return request({
      url: '/kdd/file/upload',
      method: 'post',
      data,
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }

// 获取秘钥
export function getSecrect() {
    return request({
        url: '/kdd/secret/getMine',
        method: 'post'
    })
}
