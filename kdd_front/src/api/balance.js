import request from '@/utils/request'

// 获取余额
export function getBalance(params) {
    return request({
        url: '/kdd/balance/singleQuery',
        method: 'get',
        params
    })
}
