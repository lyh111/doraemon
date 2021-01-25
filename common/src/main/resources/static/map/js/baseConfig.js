//document.write('');
var secretKey = "thinkgem,jeesite,com";

// post请求
function sendPOST(url, param, callbackFun, _config) {
    if (!param || param == null) {
        param = {};
    }
    initRequest(param, _config);
    if (!_config) {
        _config = {};
    }
    instance.post(url, _config).then(function (response) {
        if (callbackFun) {
            callbackFun(response.data);
        }
    }).catch(function (err) {
        console.log(err);
    });
}

// get请求
function sendGET(url, param, callbackFun, _config) {
    if (!param || param == null) {
        param = {};
    }
    initRequest(param, _config);
    instance.get(url, _config).then(function (response) {
        // layer.close(loading);
        if (callbackFun) {
            callbackFun(response.data);
        }
    }).catch(function (err) {
        // layer.closeAll(loading);
        console.log(err);
    });
}

var instance = undefined;

function initRequest(param, _config) {
    var baseURL = '';
    if (_config && _config.baseURL) {
        baseURL = _config.baseURL;
    }
    instance = axios.create({
        params: param,
        baseURL: baseURL
    });
    // 响应拦截（配置请求回来的信息）
    instance.interceptors.response.use(function (response) {
        // 处理响应数据
        if (response.data.message) {
            response.data['msg'] = response.data.message;
        }
        if (response.data.result == 'login') {
            $.setStorage("user", null);
            top.location.href = zw.frontPath + "/index.html";
            return response;
        } else if (response.data.result == 'true' || response.data.result == '1') {
            response.data['success'] = true;
        } else if (response.data.result == 'false') {
            response.data['success'] = false;
        }
        return response;
    }, function (error) {
        // 处理响应失败
        return Promise.reject(error);
    });

    instance.interceptors.request.use(function (config) {
        var user = $.getStorage("user");
        if (user && user != null) {
            if (config.method === 'post') {
                var data = Qs.parse(config.data);
                data.__sid = transUndefined(user.__sid);
                data.currentUserId = transUndefined(user.id);
                data.ownStreetCode = transUndefined(user.ownStreetCode);
                config.data = Qs.stringify(data);
            } else if (config.method === 'get') {
                config.params.__sid = transUndefined(user.__sid);
                config.params.currentUserId = transUndefined(user.id);
                config.params.ownStreetCode = transUndefined(user.ownStreetCode);
            }
        }
        return config;
    }, function (error) {
        // 处理响应失败
        return Promise.reject(error);
    });
}