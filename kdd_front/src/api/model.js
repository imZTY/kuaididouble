import request from '@/utils/request'
import Cookies from 'js-cookie'

// 获取单数据模型
export function reqGetSingleModel(key, params) {
  Cookies.set('key', key)
  return request({
    url: '/kdd/model/single',
    method: 'post',
    params,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
