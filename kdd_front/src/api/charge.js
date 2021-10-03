import request from '@/utils/request'

// 分页获取
export function pageList(params) {
    return request({
        url: '/kdd/charge/pageList',
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
