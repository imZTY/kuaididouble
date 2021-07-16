import request from '@/utils/request'
import qs from 'qs'

// 分页查询
export function pageList(params) {
    return request({
        url: '/kdd/queryLog/pageList',
        method: 'get',
        params
    })
}
