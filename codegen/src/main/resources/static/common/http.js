// post请求
function sendPOST(url, param, callbackFun, _config, errorCallBack) {
  if(!param || param == null) {
    param = {};
  }
  initRequest(_config);
  if(!_config) {
    _config = {};
  }
  console.log(param)
  return new Promise((resolve, reject) => {
    instance.post(url, param).then(function(response) {
      resolve(response.data);
      if(callbackFun) {
        callbackFun(response.data);
      }
    }).catch(function(error) {
      console.log(error);
      if(errorCallBack) {
        errorCallBack(error);
      }
      reject(error);
    });
})
}

// get请求
function sendGET(url, param, callbackFun, _config, errorCallBack) {
  if(!param || param == null) {
    param = {};
  }
  initRequest(_config);
  return new Promise((resolve, reject) => {
    instance.get(url, {
      params: param
    }).then(function(response) {
      resolve(response.data);
      if(callbackFun) {
        callbackFun(response.data);
      }
    }).catch(function(error) {
      console.log(error);
      if(errorCallBack) {
        errorCallBack(error);
      }
      reject(error);
    });
})
}

var instance = undefined;

function initRequest(_config) {
  var timeout = 30000;
  if(_config && _config.timeout) {
    timeout = _config.timeout;
  }
  var axiosConfig = {
    timeout: timeout // 超时时间
  };
  instance = axios.create(axiosConfig);
  // 响应拦截（配置请求回来的信息）
  instance.interceptors.response.use(function(response) {
    console.log(response.data)
    // 处理响应数据
    if(response.data.message) {
      response.data['msg'] = response.data.message;
    }
    if(response.data.result) {
      if(response.data.result == 'login') {
        // $.setStorage("user", null);
        top.location.href = zw.frontPath + "/index.html";
        return response;
      } else if(response.data.result == 'true' || response.data.result == '1') {
        response.data['success'] = true;
      } else {
        response.data['success'] = false;
        if(!_config || _config && !_config.closeErrorMsg) {
          if(response.data) {
            if(response.data.message) {
              if("No files.".indexOf(response.data.message) == -1) { // TODO 临时写法，排除附件查询时返回文件为空时弹出No files.
                layer.msg(response.data.message, {
                  icon: 5,
                  offset: ['80%', '80%'],
                  time: 10000
                });
              }
            }
          }
        }
      }
    }
    return response;
  }, function(error) {
    console.log(error.toString())
    // 处理响应失败
    if(error.toString().indexOf("timeout") != -1) {
      layer.msg("请求超时！", {
        icon: 5,
        offset: ['80%', '80%'],
        time: 10000
      });
      return Promise.reject(error);
    }
    if(error.response) {
      switch(error.response.status) {
        case '404':
          layer.msg("请求连接不存在！<br/>" + error.request.responseURL, {
            icon: 5,
            offset: ['80%', '80%'],
            time: 10000
          });
          break;
        case '504':
          layer.msg("网关超时，请检查网络连接！", {
            icon: 5,
            offset: ['80%', '80%'],
            time: 10000
          });
          break;
        default:
          if(!_config || _config && !_config.closeErrorMsg) {
            if(error.response.data) {
              error.response.data['success'] = false;
              if(error.response.data.message) {
                layer.msg(error.response.data.message, {
                  icon: 5,
                  offset: ['80%', '80%'],
                  time: 10000
                });
              }
            }
          }
      }
    }
    return Promise.reject(error);
  });

  instance.interceptors.request.use(function(config) {
    // var user = $.getStorage("user");
    if(config.method === 'post') {
      var data = Qs.parse(config.data);
      data.__sid = "";
      config.data = Qs.stringify(data, {
        arrayFormat: 'indices',
        allowDots: true
      });
    } else if(config.method === 'get') {
      config.params.isReception = 1;
      config.params.__sid = "";
    }
    return config;
  }, function(error) {
    // 处理响应失败
    return Promise.reject(error);
  });
}
