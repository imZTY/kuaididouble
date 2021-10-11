import request from '@/utils/request'
import qs from 'qs'

// 分页获取
export function pageList(params) {
    return request({
        url: '/kdd/charge/pageList',
        method: 'get',
        params
    })
}

// 获取产品收费规则
export function getAll(params) {
  return request({
      url: '/kdd/charge/getAll',
      method: 'get',
      params
  })
}

// 修改
export function update(data) {
    data = qs.stringify(data)
    return request({
      url: '/kdd/charge/update',
      method: 'post',
      data,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
}

// 新增
export function add(data) {
    data = qs.stringify(data)
    return request({
      url: '/kdd/charge/add',
      method: 'post',
      data,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
}

// 绑定
export function link(data) {
    data = qs.stringify(data)
    return request({
      url: '/kdd/charge/link',
      method: 'post',
      data,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
}

// 解绑
export function unlink(data) {
  data = qs.stringify(data)
  return request({
    url: '/kdd/charge/unlink',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}