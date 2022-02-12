import request from '@/utils/request'
import qs from 'qs'

/**
 * 在新窗口发送post请求
 * @param {String} URL ${contextPath}/analyse/detail.do
 * @param {Map} PARAMS {carNum :carNum,ids:refIds}
 */
function postInNewTab(URL, PARAMS) { 
    var temp_form = document.createElement("form")    
    temp_form.action = URL     
    temp_form.target = "_blank"
    temp_form.method = "post"     
    temp_form.style.display = "none"
    for (var x in PARAMS) { 
        var opt = document.createElement("textarea")     
        opt.name = x
        opt.value = PARAMS[x]     
        temp_form.appendChild(opt)     
    }     
    document.body.appendChild(temp_form)     
    temp_form.submit()
    // 删除使用完成的表单
    var formParent = temp_form.parentNode//获取input的父对象
    formParent.removeChild(temp_form)
}

// 创建支付订单 - 电脑web支付
export function pcPay(data) {
    data = qs.stringify(data)
    return request({
        url: '/kdd/order/pcPay',
        method: 'post',
        data
    })
}


// 订单查询 - 跟进订单状态
export function queryAndRefresh(data) {
    data = qs.stringify(data)
    return request({
        url: '/kdd/order/queryAndRefresh',
        method: 'post',
        data
    })
}

// 超管账号充值
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
