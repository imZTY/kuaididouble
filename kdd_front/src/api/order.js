import request from '@/utils/request'
import qs from 'qs'

// 充值
export function charge(data) {
    data = qs.stringify(data)
    return request({
        url: '/kdd/order/charge',
        method: 'post',
        data
    })
}

// 分页查询
export function pageList(params) {
    return request({
        url: '/kdd/order/pageList',
        method: 'get',
        params
    })
}
