import request from '@/utils/request'
import qs from 'qs'

// 分页获取
export function pageList(params) {
    return request({
        url: '/kdd/product/pageList',
        method: 'get',
        params
    })
}

// 修改
export function update(data) {
    data = qs.stringify(data)
    return request({
      url: '/kdd/product/update',
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
      url: '/kdd/product/add',
      method: 'post',
      data,
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
}
