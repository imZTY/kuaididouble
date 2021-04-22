import request from '@/utils/request'
import qs from 'qs'
function arr2Obj(url, key, arr) {
  if (arr.length === 0) return
  if (arr.length > 1) {
    arr.map(item => {
      url = url.concat(`${key}=${item}&`)
    })
    // url = url.slice(0, url.length - 1)
  } else if (arr.length === 1) {
    url = url.concat(`${key}=${ids[0]}`)
  }
  return url
}

// 获取CTG数据
export function reqGetCtgData(params) {
  let url = '/kdd/search/ctgData/byFilter?'
  Object.keys(params).forEach(key => {
    const value = params[key]
    if (Array.isArray(value)) {
      url = arr2Obj(url, key, value)
    } else if (value != null) {
      url = url.concat(`${key}=${value}&`)
    }
  })
  url = url.slice(0, url.length - 1)
  return request({
    url,
    method: 'get'
  })
}

// 获取单条ctg详细数据
export function reqGetCtgDetail(params) {
  return request({
    url: '/kdd/search/ctgData/detail',
    method: 'get',
    params
  })
}

// 获取导入历史
export function reqGetHistory(params) {
  return request({
    url: '/kdd/search/history',
    method: 'get',
    params
  })
}

// 获取所有医师信息列表
export function reqGetDoctor(params) {
  return request({
    url: '/kdd/search/doctor',
    method: 'get',
    params
  })
}

// 上传CTG数据
export function uploadCtg(data) {
  return request({
    url: '/kdd/task/upload/ctgdata',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
// 上传模型数据
export function uploadModel(data) {
  return request({
    url: '/kdd/task/upload/pyModel',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
// 上传曲线图数据
export function uploadLines(data) {
  return request({
    url: '/kdd/task/upload/lines',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 添加曲线标注
export function addMark(data) {
  return request({
    url: '/kdd/mj/add',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },

    method: 'post',
    data: qs.stringify(data, { arrayFormat: 'brackets' })
  })
}

// 修改曲线标注
export function updateMark(data) {
  return request({

    url: '/kdd/mj/update',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    method: 'post',
    data: qs.stringify(data, { arrayFormat: 'brackets' })
  })
}

