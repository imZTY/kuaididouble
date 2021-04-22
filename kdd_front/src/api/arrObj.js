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
