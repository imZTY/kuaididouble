import request from '@/utils/request'
import qs from 'qs'

export function register(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/account/register',
    method: 'post',
    data
  })
}

export function login(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/account/login',
    method: 'post',
    data
  })
}

export function getUserList(params) {
  return request({
    url: '/kdd/account/pageList',
    method: 'get',
    params
  })
}
export function reqSetRole(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/role/setRole',
    method: 'post',
    data
  })
}

// 修改用户资料
export function reqEditUser(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/userInfo/updateMine',
    method: 'post',
    data
  })
}

// 修改账号信息，如密码
export function reqEditAccount(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/account/update',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/kdd/account/logout',
    method: 'post'
  })
}

export function getSecretKey(data) {
  return request({
    url: '/kdd/secret/getMine',
    method: 'post',
    data
  })
}
