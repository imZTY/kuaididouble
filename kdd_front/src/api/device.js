import request from '@/utils/request'

// 获取数据接口记录
export function reqGetDeviceHistory(params) {
	  return request({
    url: '/kdd/device/historyByPage',
    method: 'get',
    params
  })
}

// 修改设备
export function reqEdiMachine(params) {
	  return request({
    url: '/kdd/device/update',
    method: 'post',
    params
  })
}

// 停用设备
export function reqDelMachine(params) {
	  return request({
    url: '/kdd/device/delete',
    method: 'post',
    params
  })
}

// 分页获取
export function reqGetMachine(params) {
	  return request({
    url: '/kdd/device/listByPage',
    method: 'get',
    params
  })
}

// 添加设备
export function reqAddMachine(params) {
	  return request({
    url: '/kdd/device/add',
    method: 'post',
    params
  })
}
