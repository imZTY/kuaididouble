import request from '@/utils/request'
import qs from 'qs'

export function getRoleList(params) {
  return request({
    url: '/kdd/role/pageList',
    method: 'get',
    params
  })
}

export function reqAddRole(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/role/add',
    method: 'post',
    data
  })
}
export function reqEditRole(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/role/update',
    method: 'post',
    data
  })
}
export function reqDelRole(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/role/delete',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/kdd/user/logout',
    method: 'post'
  })
}
