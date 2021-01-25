/**
 * 公共JS文件
 */
var search_list_SubHeigth = 170;//综合检索时所有检索出的列表页面要减去的高度
var search_infoBox_SubHeigth = 120//综合检索时search-info-box要减去的高度
var search_info_SubHeigth = 170//综合检索时.search-list要减去的高度（searchInfo的高度）
var fileSuffix = ["gif", "jpg", "png"];// 图片后缀
var advanChara = "#";
var advanHotChara = "热点查询";
var switcherys = [];
var basicParam = {
    "residentParam": {
        "saveUrl": "/web/resident/save",
        "sex": {"0": "男", "1": "女", "2": "未知", "3": "其他"},
        "cardType": {
            "certificateType001": "身份证",
            "certificateType002": "港澳台证",
            "certificateType003": "外籍护照",
            "certificateType004": "华侨证",
            "certificateType005": "军人证件",
            "certificateType006": "其他"
        },
        "politics": {
            "politicalStatus001": "群众",
            "politicalStatus002": "党员",
            "politicalStatus003": "团员",
            "politicalStatus004": "其他"
        },
        "education": {
            "education006": "小学",
            "education007": "初中",
            "education008": "高中",
            "education001": "专科",
            "education002": "大学本科",
            "education003": "硕士",
            "education005": "其他"
        },
        "workStatus": {
            "employmentStatus001": "在职",
            "employmentStatus003": "无业",
            "employmentStatus004": "退休",
            "employmentStatus005": "待业",
            "employmentStatus006": "个体",
            "employmentStatus007": "下岗",
            "employmentStatus008": "其他"
        },
        "marriage": {
            "marriage001": "已婚",
            "marriage002": "未婚",
            "marriage003": "初婚",
            "marriage004": "丧偶",
            "marriage005": "离异",
            "marriage006": "再婚",
            "marriage007": "离婚",
            "marriage008": "复婚",
            "marriage009": "其它"
        },
        "healthStatus": {"health001": "健康", "health002": "非健康"},
        "nation": {
            "nation001": "汉族",
            "nation002": "蒙古族",
            "nation003": "回族",
            "nation004": "藏族",
            "nation005": "维吾尔族",
            "nation006": "苗族",
            "nation007": "彝族",
            "nation008": "壮族",
            "nation009": "布依族",
            "nation010": "朝鲜族",
            "nation011": "满族",
            "nation012": "侗族",
            "nation013": "瑶族",
            "nation014": "白族",
            "nation015": "土家族",
            "nation016": "哈尼族",
            "nation017": "哈萨克族",
            "nation018": "傣族",
            "nation019": "黎族",
            "nation020": "傈僳族",
            "nation021": "佤族",
            "nation022": "畲族",
            "nation023": "高山族",
            "nation024": "拉祜族",
            "nation025": "水族",
            "nation026": "东乡族",
            "nation027": "纳西族",
            "nation028": "景颇族",
            "nation029": "柯尔柯孜族",
            "nation030": "土族",
            "nation031": "达尔族",
            "nation032": "仫佬族",
            "nation033": "羌族",
            "nation034": "布朗族",
            "nation035": "撒拉族",
            "nation036": "毛南族",
            "nation037": "仡佬族",
            "nation038": "锡伯族",
            "nation039": "阿昌族",
            "nation040": "普米族",
            "nation041": "塔尔克族",
            "nation042": "怒族",
            "nation043": "乌孜别克族",
            "nation044": "俄罗斯族",
            "nation045": "鄂温克族",
            "nation046": "德昂族",
            "nation047": "保安族",
            "nation048": "裕固族",
            "nation049": "京族",
            "nation050": "塔塔尔族",
            "nation051": "独龙族",
            "nation052": "鄂伦春族",
            "nation053": "赫哲族",
            "nation054": "门巴族",
            "nation055": "珞巴族",
            "nation097": "其他未识别的民族",
            "nation098": "外国人加入中国籍（入籍）"
        }

    },
    "houseParam": {
        "saveUrl": "/web/house/save",
        "houseNature": {
            "houseNature001": "房改房",
            "houseNature002": "商品房",
            "houseNature003": "公租房",
            "houseNature004": "经适房",
            "houseNature005": "其他"
        },
        "designUse": {
            "houseUseful001": "自住",
            "houseUseful002": "商业",
            "houseUseful003": "出租",
            "houseUseful004": "办公",
            "houseUseful005": "教育",
            "houseUseful006": "娱乐",
            "houseUseful007": "其他"
        },
        "houseType": {
            "一居室": "一居室",
            "两居室": "两居室",
            "三居室": "三居室",
            "四居室": "四居室",
            "五居室": "五居室",
            "六居室": "六居室",
            "别墅": "别墅",
            "其他": "其他"
        }
    }
};

advan_color = ["layui-bg-orange", "layui-bg-green", "layui-bg-cyan", "layui-bg-blue", "layui-bg-black", "layui-bg-gray"];
advan_color1 = ["ffcdd2", "F8BBD0", "E1BEE7", "D1C4E9", "FF8A65", "A1887F"];

function loadPerPage(containerId, url, isFrame, type, list_url, callFun) {
    //$('#' + containerId).showLoading();visible
    $('#mainContext').css('visibility', 'hidden');
    if (!$('#listChildContext').hasClass("display_none")) {
        $("#listChildContext").removeClass("bounceOutRight animated loading").addClass("bounceOutRight animated").one("webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend", function () {
            $(this).removeClass("bounceOutRight animated").css("left", "10000px").empty();
            var e = e || window.event;
            e.stopPropagation();
        });
    }
    isMapShow = false;
    $('#changeTypeBtn').attr('title', '切换至列表').addClass('disabled');
    $('#listContext').empty();
    $('#listContext').addClass('display_none').css('left', '10000px');
    isHome = false;
    if (typeof (arguments[2]) == 'undefined') {
        isFrame = false;
    }
    if (typeof (arguments[3]) == 'undefined') {
        type = '';
    }
    if (isFrame) {
        isMapShow = true;
        $('#mainContext').css('visibility', 'visible');
        //if(url == "home.html"){
        clearInterval(sliderInterval);
        //}
        if (type != '') {
            url = url + '?type=' + type;
            $('#changeTypeBtn').attr('title', '切换至列表').removeClass('disabled');
            $('#changeTypeBtn i').removeClass('icon-map').addClass('icon-list');
        }
        $('#' + containerId).empty().css('padding', '0').html('<iframe id="mainFrame" name="mainFrame" src="' + url + '" width="100%" height="100%" scrolling=no frameborder=0></iframe>');
        // 先显示列表隐藏
        if (callFun) {
            var mainFrame = document.getElementById("mainFrame");
            mainFrame.onload = function () {
                //search();
                callFun()
            }
        }
        if (list_url) {
            containerId = 'listContext';
            $('#' + containerId).empty();
            $('#listContext').removeClass('display_none').css('left', '0px');
            $('#changeTypeBtn').attr('title', '切换至地图').removeClass('disabled');
            $('#changeTypeBtn i').removeClass('icon-list').addClass('icon-map');
            $('#' + containerId).load(list_url, function () {
                listShowFlag = true;
                $(this).removeClass('loading');
            });
        }
    } else { // 纯列表没有地图时
        if (url == user.defaultUrl || ((!user.defaultUrl || user.defaultUrl.trim() == '') && url == 'home.html')) {
            isHome = true;
        } else {
            clearInterval(sliderInterval);
        }
        if (!listShowFlag) {
            $('#' + containerId).empty();
            $('#listContext').removeClass('display_none').css('left', '0px');
            $('#changeTypeBtn').attr('title', '切换至地图').removeClass('disabled');
            $('#changeTypeBtn i').removeClass('icon-list').addClass('icon-map');
            containerId = 'listContext';
            $('#' + containerId).load(url, function () {
                //$('#' + containerId).hideLoading();
                $('#mainContext').css('visibility', 'visible');
            });
        } else {
            $('#' + containerId).css('padding', '30px').css('padding-bottom', '0').load(url, function () {
                //$('#' + containerId).hideLoading();
                $('#mainContext').css('visibility', 'visible');
            });
        }
    }
    /*if (obj) {
        $('#secondNavTag').text(obj.text || obj);
        $('#secondNavTag').unbind();
        $('#secondNavTag').bind('click', function() {
            loadPerPage(containerId, url, obj.text);
        });
    }*/
}

// 验证
var afterErrorNode;
var afterNodeMsg;

function vaildata(vOption) {
    var submitBtns = "#submit";
    var vaildataArea = "#detail_infoBox_form";
    if (vOption && vOption.submitBtns) {
        submitBtns = vOption.submitBtns;
    }
    if (vOption && vOption.vaildataArea) {
        vaildataArea = vOption.vaildataArea;
    }
    ac.form({
        area: vaildataArea, // 验证区域，支持标签，id，class，推荐使用id或者class
        btn: submitBtns, // 触发验证的按钮或者元素，支持标签，id，class，推荐使用id或者class
        singleError: function (e, msg) {
            if (afterErrorNode != undefined) {
                $(afterErrorNode).siblings("span").removeClass("text-danger").html(afterNodeMsg);
            }
            afterErrorNode = e;
            afterNodeMsg = $(e).siblings("span").html();
            //$(e).siblings("span").addClass("text-danger").html($(e).parent().siblings("label").html() + msg);
            $(e).siblings("span").addClass("text-danger").html(msg);
            console.log($(e).siblings("span"))
            if ($(e).siblings("span").length == '0') {
                // $(".wryFoeEx").addClass('display_none')
                $(".wryFoeEx").html(msg);
                $(".wryFoeEx").addClass('red')
                //$(e).after("<span style='color: #f00;'>"+msg+"</span>");
            }
            if (vOption && vOption.errorFun) {
                vOption.errorFun(e, msg);
            }
        },
        endSuccess: function (data, currObj) {
            if (afterErrorNode != undefined) {
                $(afterErrorNode).siblings("span").removeClass("text-danger").html(afterNodeMsg);
            }
            if (vOption && vOption.submitBeforeFun) {
                vOption.submitBeforeFun(data);
            }
            var fun = eval($(currObj).attr("data-fun"));
            if (vOption && vOption.successFun) {
                vOption.successFun(data);
            }
        }
    });
}

// 退出登录

function logout() {
    layer.confirm("是否退出登录？", {
            btn: ['确定', '取消'] //按钮
        },
        function (index) {
            sendGET("a/logout", undefined, function (res) {
                layer.close(index);
                $.setStorage('user', null);
                top.location.href = 'index.html';
            });
        });
}

function getLevelByAQI(aqi_) {
    var level = 0;
    var aqi = parseInt(aqi_);
    switch (aqi > 0) {
        case aqi <= 50:
            level = 1;
            break;
        case aqi <= 100:
            level = 2;
            break;
        case aqi <= 150:
            level = 3;
            break;
        case aqi <= 200:
            level = 4;
            break;
        case aqi <= 300:
            level = 5;
            break;
        default:
            level = 6;
            break;
    }
    return level;
}

function showIndexWeather(d) {
    //$('.weather').fadeOut();
    var weather = d.results[0];
    $('#pm25 font').html(weather.pm25);
    var pm25 = parseInt(weather.pm25);
    var kqzl = '优';
    var kqys = '#2ecc71';
    switch (true) {
        case pm25 <= 50:
            kqzl = '优';
            kqys = '#2ecc71';
            break;
        case pm25 <= 100:
            kqzl = '良';
            kqys = '#F8D70C';
            break;
        case pm25 <= 150:
            kqzl = '轻度污染';
            kqys = '#ffb136';
            break;
        case pm25 <= 200:
            kqzl = '中度污染';
            kqys = '#e74a25';
            break;
        case pm25 <= 300:
            kqzl = '重度污染';
            kqys = '#8F006D';
            break;
        default:
            kqzl = '严重污染';
            kqys = '#322275';
            break;
    }
    $('#pm25 small').html(kqzl);
    $('#pm252 small').html(kqzl);
    $('#pm25').css('background', kqys);
    $('#pm252').css('background', kqys);
    var curr_weather = weather.weather_data[0];
    var reg_temp = /：(.*?)℃/gi;
    var curr_temp = reg_temp.exec(curr_weather.date)[1];
    $('#rtTemp').html(curr_temp);
    $('#rtTemp2').html(curr_temp);
    $('#temperature').html(curr_weather.temperature);
    $('#temperature2').html(curr_weather.temperature);
    $('#weather_').html(curr_weather.weather);
    $('#weather_2').html(curr_weather.weather);
    //$('.weather').fadeIn();
}

function getWeather(callback) {
    $.ajax({
        url: "http://api.map.baidu.com/telematics/v3/weather?location=郑州&output=json&ak=9kyhDI0YZPNEk3O6myQcZhNYDqhTXEQx",
        dataType: "jsonp",
        jsonpCallback: "showIndexWeather",
        success: function (data) {
            //alert(JSON.stringify(data));
        }
    });
}

/**
 * 设置数据
 * @param {Object} Data
 */
function setData(Data, selectValArr, objId) {
    if (!objId) {
        objId = '#detail_infoBox_form';
    }
    var data = Data.data;
    setSelect(Data, objId, selectValArr);
    // 设置text类型数据
    setText(data, objId);
    // 设置textarea类型数据
    setHtml(data, ["textarea"], objId);
    //设置checkbox类型
    setCheckBox(data, objId);
}

function setCheckBox(data, objId) {
    if (objId == undefined) {
        objId = "";
    }
    var checkboxnumber = $("" + objId + " input[type='checkbox']");
    for (var i = 0; i < checkboxnumber.length; i++) {
        value = $(checkboxnumber[i]).attr("name");
        $(checkboxnumber[i]).val("");
        for (var item in data) {
            if (value != undefined && value == item) {
                var _data = data[item];
                if (item == value && _data == "1" && _data != null) {
                    $(checkboxnumber[i]).attr("checked", "checked");
                } else {
                    $(checkboxnumber[i]).remove("checked");
                }
                break;
            }
        }
    }
}

/**
 * 设置text文本
 * @param {Object} data 单条记录
 */
function setText(data, objId) {
    var value;
    if (objId == undefined) {
        objId = "";
    }
    var texts = $("" + objId + " input[type='text']");
    var hiddens = $("" + objId + " input[type='hidden']");
    var dates = $("" + objId + " input[type='date']");
    var datetimes = $("" + objId + " input[type='datetime']");
    var datenumber = $("" + objId + " input[type='number']");
    setContent(texts, data);
    setContent(hiddens, data);
    setContent(dates, data);
    setContent(datetimes, data);
    setContent(datenumber, data);
}

function setContent(content, data) {
    // 设置text类型数据
    for (var i = 0; i < content.length; i++) {
        value = $(content[i]).attr("name");
        $(content[i]).val("");
        for (var item in data) {
            if (value != undefined && value == item) {
                var _data = data[item];
                if ($(content[i]).attr('type') == 'date' && _data != null) {
                    _data = _data.split(" ")[0];
                }
                $(content[i]).val(_data);
                break;
            }
        }
    }
}

/**
 * 设置文本
 * @param {Object} data 要渲染的数据
 * @param {Object} selectObjArray 节点数据如["td","span"]
 * @param {Object} objId 父节点（在父节点中渲染）
 */
function setHtml(data, selectObjArray, objId, setlength) {
    if (objId == undefined) {
        objId = "";
    }
    if (selectObjArray == undefined) {
        selectObjArray = [];
    }
    var value;
    for (var i = 0; i < selectObjArray.length; i++) {
        var selectObjs = $(objId + " " + selectObjArray[i]);
        for (var j = 0; j < selectObjs.length; j++) {
            value = $(selectObjs[j]).attr("name");
            for (var item in data) {
                if (value != undefined && value == item) {
                    if (item == "sex") {
                        if (data[item] == '0') {
                            data[item] = "男";
                        } else {
                            data[item] = "女";
                        }
                    }
                    var id = $(selectObjs[j]).attr('id');
                    var preLength = $("#" + id).prev().text().length;
                    //console.log($("#"+id).prev().text().length);
                    //console.log(simplifyValue(data[item],2));
                    //console.log(simplifyValue(data[item]+"".length));
                    if (setlength != null || setlength != undefined) {
                        if (preLength == 2) {

                            $("#" + id).html(simplifyValue(data[item] + "", 5));

                        } else if (preLength == 3) {
                            $("#" + id).html(simplifyValue(data[item] + "", 3));
                        } else if (preLength == 4) {
                            $("#" + id).html(simplifyValue(data[item] + "", 2));
                        }
                        $("#" + id).parent().attr('title', "总数:" + data[item])
                    } else {
                        $(selectObjs[j]).html(data[item]);
                    }
                    break;
                }
            }
        }
    }
}

/**
 *
 * @param {Object} sData 带有下拉表的数据（id为要插入数据的位置，name为默认值）
 * @param {Object} data 记录
 */
function setSelect(sData, objId, selectValArr) {
    if (objId == undefined) {
        objId = "";
    }
    var data = sData.data;
    var value;
    var selects = $("" + objId + " select");
    var html = "";
    for (var item in sData) {
        for (var i = 0; i < selects.length; i++) {
            // 添加option
            value = $(selects[i]).attr("id");
            var defaultValue = $(selects[i]).attr("name");
            var defaultId;
            if (value != undefined && value == item) {
                var _valName = "id";
                var _valCode = "code";
                for (var valName in selectValArr) {
                    if (valName == value) {
                        _valName = selectValArr[valName];
                        break;
                    }
                }
                html = "";
                // 设置默认值
                for (var item1 in data) {
                    if (defaultValue != undefined && defaultValue == item1) {
                        defaultId = data[item1];
                        break;
                    }
                }
                // 拼接option
                for (var j = 0; j < sData[item].length; j++) {
                    if (defaultId == sData[item][j][_valName]) {
//                      html = html + "<option selected value='" + sData[item][j][_valName] + "'>" + sData[item][j].name + "</option>";
                        if (sData[item][j][_valCode]) {
                            html = html + "<option selected code='" + sData[item][j][_valCode] + "' value='" + sData[item][j][_valName] + "'>" + sData[item][j].name + "</option>";
                        } else {
                            html = html + "<option selected value='" + sData[item][j][_valName] + "'>" + sData[item][j].name + "</option>";
                        }
                    } else {
                        if (sData[item][j][_valCode]) {
                            html = html + "<option code='" + sData[item][j][_valCode] + "' value='" + sData[item][j][_valName] + "'>" + sData[item][j].name + "</option>";
                        } else {
                            html = html + "<option value='" + sData[item][j][_valName] + "'>" + sData[item][j].name + "</option>";
                        }
                    }
                }
                $(selects[i]).append(html);
            } else {
                if (data != undefined) {
                    for (var item1 in data) {
                        if (defaultValue != undefined && defaultValue == item1) {
                            $(selects[i]).children("[value='" + data[item1] + "']").attr("selected", true);
                            break;
                        }
                    }
                }
            }
        }
    }
}

// 选中默认值
function initSelect(selectObj, defaultValue) {
    var options = $(selectObj + " option");
    for (var i = 0; i < options.length; i++) {
        if (options[i].value == defaultValue) {
            options[i].selected = "selected";
            break;
        }
    }
}

// 初始化radio对象
function initRadio(obj, defaultVal) {
    var radios = $(obj);
    $.each(radios, function (i, item) {
        if (item.value == defaultVal) {
            radios[i].checked = "checked";
        }
    });
}

(function ($) {
    $.extend({
        strToJson: function (str) {
            if (typeof str === 'string') {
                return JSON && JSON.parse(str);
            }
        },
        jsonToStr: function (json) {
            if (typeof json === 'object') {
                return JSON && JSON.stringify(json);
            }
        },
        setStorage: function (key, value) {
            if (arguments.length === 2) {
                var v = value;
                if (typeof v == 'object') {
                    v = JSON.stringify(v);
                    v = 'obj-' + v;
                } else {
                    v = 'str-' + v;
                }
                var ls = window.localStorage;
                if (ls) {
                    ls.setItem(key, v);
                }
            }
        },
        getStorage: function (key) {
            var ls = window.localStorage;
            if (ls) {
                var v = ls.getItem(key);
                if (!v) {
                    return;
                }
                if (v.indexOf('obj-') === 0) {
                    v = v.slice(4);
                    return JSON.parse(v);
                } else if (v.indexOf('str-') === 0) {
                    return v.slice(4);
                }
            }
        },
        rmStorage: function (key) {
            var ls = window.localStorage;
            if (ls && key) {
                ls.removeItem(key);
            }
        },
        clearStorage: function () {
            var ls = window.localStorage;
            if (ls) {
                ls.clear();
            }
        }
    });
})(jQuery);

$.prototype.serializeObject = function () {
    var a, o, h, i, e;
    a = this.serializeArray();
    o = {};
    h = o.hasOwnProperty;
    for (i = 0; i < a.length; i++) {
        e = a[i];
        if (!h.call(o, e.name)) {
            o[e.name] = e.value;
        }
    }
    return o;
};

(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
})(jQuery);

function errorLayer() {
    layer.open({
        type: 1,
        title: false //不显示标题栏
        ,
        closeBtn: false,
        area: '600px;',
        shade: 0.8,
        id: 'errorLayer',
        content: '<div style="padding: 50px; line-height: 42px; background-color: #393D49; color: #fff; font-weight: 300; font-size:36px; ">非常抱歉，暂时无法显示 -.-</div>'
    });
}

function makeGallery(imgList, defalutUrl) {
    if (imgList instanceof String) {
        imgList = imgList.split("|");
    }
    $('.fs_gallery').remove();
    $('#topGallery').empty();
    for (var i = 0; i < imgList.length; i++) {
        $('#topGallery').append('<img id="topGalleryImg' + i + '" src="' + imgList[i] + '"/>');
    }
    $('#topGallery img').fsgallery();
    $('#topGalleryImg1').click();
    $('.fs_gallery').css('display', 'block');
    $('.fs_gallery').css({'display': 'block', 'z-index': '198910161'});
    $('.fs_gallery_prev').click();
    if (defalutUrl) {
        var count = -1;
        for (var i = 0; i < imgList.length; i++) {
            if (imgList[i] == defalutUrl) {
                count = i;
            }
        }
        if (count > 0) {
            for (var j = 0; j < count; j++) {
                $('.fs_gallery_next').click();
            }
        }
    }
    $('.fs_gallery_close').css('top', '70px');
    changeColor();
    // var imgs = document.getElementsByClassName("fs_gallery_shuft").getElementsByTagName("img")
    // for(let item of imgs){
    // if(item.attr("src").indexOf('sign')>0){
    // 	this.parent().css('backgroundColor','#fff')
    // 	}
    // }
    // var imglist=$(".fs_gallery_shuft").find("div");//获取ID为div里面的所有img
    // for(var i=0;i<imglist.length;i++){ //循环为每个img设置
    //    //imglist[i].attr("src","图片地址");
    //    console.log(imglist[i].attr())
    // }

}

function changeColor() {
    var imglist = $(".fs_gallery_shuft").find("img");//获取ID为div里面的所有img
    for (var i = 0; i < imglist.length; i++) { //循环为每个img设置
        //imglist[i].attr("src","图片地址");
        console.log(imglist[i].attr())
    }
}

// 把null值改为空字符串
function ifNull(array, defaultVal) {
    $.each(array, function (i, item) {
        if (item == null) {
            if (defaultVal != undefined) {
                array[i] = defaultVal;
            } else {
                array[i] = "---";
            }
        }
    });
}

//集合items内容如为undefined则改为默认值
function ifUndefined(obj, items, defaultVal) {
    $.each(items, function (i, val) {
        if (obj[val] == undefined) {
            if (defaultVal != undefined) {
                obj[val] = defaultVal;
            } else {
                obj[val] = "---";
            }
        }
    });
}

function transUndefined(value) {
    if (!value) {
        return "";
    } else {
        return value;
    }
}

// 明细框取消按钮点击
function cancleDetailBtn_click() {

    $('#detail_infoBox').removeClass('bounceOutRight animated').addClass('bounceOutRight animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
        var e = e || window.event;
        e.stopPropagation();
        $(this).removeClass("bounceOutRight animated").addClass('display_none');
        $('#detail_infoBox_form').empty();
        if ($('.typeBtns, .flyout-wrap').length != 0) {
            $('.typeBtns, .flyout-wrap').fadeIn();
            map.removeOverlay(tmp_overlays);
        }
        if (curr_Marker) {
            curr_Marker.setAnimation(null);
            if (curr_add.id != null && (curr_Marker.type == "gongdi" || curr_Marker.type == "pollution")) {
                curr_Marker.setIcon(mapIcon[curr_Marker.type][grade]);
            }
        }
    });
}

// 列表的编辑页面
var flag  // 列表标志位
function loadDetailHtml(id, type, title, contentId) {
    if (!contentId || contentId == null) {
        contentId = "detail_infoBox_form";
    }
    curr_Marker = undefined;
    flag = true;
    curr_add.type = type;
    curr_add.title = title;
    if (id) {
        curr_add.id = id;
    } else {
        curr_add.id = null;
    }
    if (type == "teshu" || type == "xinfang" || type == "jiufen" || type == "zhengzhi") {
        type = "zongzhi_" + type;
    } else if (type == "gongdi") {
        type = "pollution";
    } else if (type == "video") {
        type = "bujian_" + type;
    } else if (type == "bujian") {
        type = "bujian_normal";
    } else if (type == "zhongdian") {
        type = "bujian_zhongdian";
    }
    $('#' + contentId).load('./html/mapAll/detail_infoBox_' + type + '.html?tmp=' + Date.parse(new Date()), function () {
        //$('.panel-heading span').html(title);
        $('#detail_infoBox').removeClass('display_none bounceInRight animated').addClass('bounceInRight animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            var e = e || window.event;
            e.stopPropagation();
            $(this).removeClass("bounceInRight animated");
            $('#detail_info').slimScroll({
                height: $('#detail_infoBox').height() - 60 - 40,
                width: '100%'
            });
        });
    });
}

//*********************照片形式转换与显示**********************//
function photoCompress(file, w, objDiv, domId, picId, tag) {
    if (!tag) {
        tag = 'div';
    }
    var ready = new FileReader();
    /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
    ready.readAsDataURL(file);
    ready.onload = function () {

        var result = document.getElementById('div' + domId);
        ++n;
        if (picId != "" && picId != undefined && picId != "undefined") {
            picIdS.push(picId)
            var length = $("#" + domId).parent().parent().children().length;
            if ($("#" + domId).parent().parent().children().length < childrenNum + 1) {
                $("#" + domId).next('img').attr("src", this.result)
                // $("#" + domId).parent().parent().append(`
                // 	<${tag} class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${length+1}">
                // <input id="file${length+1}" class="divInnerFile" type="file" class="commonAttachId" onchange="comppressPic('file${length+1}')"/>
                // </${tag}>
                // `)
            }
        } else {
            // alert(1111)
            if (form.has("file" + domId)) {

            } else {
                var length = $("#" + domId).parent().parent().children().length;
                var prediv = $("#" + domId).parent().attr('id')//获取this的上一个div的id
                //var preNum = prediv.substr(prediv.length-1,1);//截取上一个div的id后面的数字以便于后面元素id的添加
                //var allDiv = $("#" + domId).parent().siblings();
                //var parentChil = $("#" + domId).parent().parent().children();
                var n = $("#" + domId).parent().parent().children().eq(0);//现获取第一个div dom 然后获取所有div中id后面的数字
                var divNumArr = [];
                for (let i = 0; n, i < length; n = n.next(), i++) {
                    console.log(n.attr('id'));
                    var id = n.attr('id');
                    divNumArr.push(id.substr(id.length - 1, 1))
                }
                var nextDicName = Math.max.apply(null, divNumArr);
                console.log(nextDicName)
                if ($("#" + domId).parent().parent().children().length < childrenNum) {
                    $("#" + domId).parent().parent().append(`
									<${tag} class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${parseInt(nextDicName) + 1}">
									
									<input id="file${parseInt(nextDicName) + 1}" class="divInnerFile" type="file" class="commonAttachId" onchange="comppressPic('file${parseInt(nextDicName) + 1}','${picId}','${tag}')"/>
								</${tag}>
												`)
                }
            }

            //显示文件
            if ($("#div" + domId).find("img").length == 0) {
                // $("#div"+domId).append('<span class="delIcon" onclick="addDeletePic('+'"file'+domId+'",this)">X</span><img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />')
                $("#div" + domId).append(`
                            	<span class="delIcon" onclick="addDeletePic('file${domId}',this,1)">X</span><img style="width:50px;height: 50px;" src="${this.result}"/>
                            `);
            } else {
                $("#div" + domId).find("img").attr('src', this.result);
            }
            //result.innerHTML = '<img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />';
        }
        var re = this.result;
        canvasDataURL(re, w, objDiv)

    }
}

function photoCompressTwo(file, w, objDiv, domId, picId, tag) {
    if (!tag) {
        tag = 'div';
    }
    var ready = new FileReader();
    /*开始读取指定的Blob对象或File对象中的内容. 当读取操作完成时,readyState属性的值会成为DONE,如果设置了onloadend事件处理程序,则调用之.同时,result属性中将包含一个data: URL格式的字符串以表示所读取文件的内容.*/
    ready.readAsDataURL(file);
    ready.onload = function () {

        var result = document.getElementById('div' + domId);
        //++n;
        specialN++;
        if (picId != "" && picId != undefined && picId != "undefined") {
            picIdSTwo.push(picId)
            var length = $("#" + domId).parent().parent().children().length;
            if ($("#" + domId).parent().parent().children().length < childrenNum + 1) {
                $("#" + domId).next('img').attr("src", this.result)
                // $("#" + domId).parent().parent().append(`
                // 	<div class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${length+1}">
                // <input id="file${length+1}" class="divInnerFile" type="file" class="commonAttachId" onchange="photoCompressTwo('file${length+1}')"/>
                // </div>
                // `)
            }
        } else {
            if (formTwo.has("file" + domId)) {

            } else {
                var length = $("#" + domId).parent().parent().children().length;
                var prediv = $("#" + domId).parent().attr('id')//获取this的上一个div的id
                //var preNum = prediv.substr(prediv.length-1,1);//截取上一个div的id后面的数字以便于后面元素id的添加
                //var allDiv = $("#" + domId).parent().siblings();
                //var parentChil = $("#" + domId).parent().parent().children();
                var n = $("#" + domId).parent().parent().children().eq(0);//现获取第一个div dom 然后获取所有div中id后面的数字
                var divNumArr = [];
                for (let i = 0; n, i < length; n = n.next(), i++) {
                    console.log(n.attr('id'));
                    var id = n.attr('id');
                    divNumArr.push(id.substr(7))
                }
                var nextDicName = Math.max.apply(null, divNumArr);
                console.log(nextDicName)
                if ($("#" + domId).parent().parent().children().length < childrenNum) {
                    $("#" + domId).parent().parent().append(`
						<${tag} class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${specialN}">
						<input id="file${specialN}" class="divInnerFile" type="file" class="commonAttachId" onchange="comppressPicTwo('file${specialN}','${picId}','${tag}')"/>
					</${tag}>
					`)
                }
            }


            //显示文件
            //result.innerHTML = '<img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />';
            //$("#div"+domId).append('<img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />')
            if ($("#div" + domId).find("img").length == 0) {
                // $("#div"+domId).append('<img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />')
                $("#div" + domId).append(`
                            	<span class="delIcon" onclick="addDeletePic('file${domId}',this,2)">X</span><img style="width:50px;height: 50px;" src="${this.result}"/>
                            `);
            } else {
                $("#div" + domId).find("img").attr('src', this.result);
            }
        }
        var re = this.result;
        canvasDataURL(re, w, objDiv)

    }
}

function canvasDataURL(path, obj, callback) {
    var img = new Image();
    img.src = path;
    img.onload = function () {
        var that = this;
        // 默认按比例压缩
        var w = that.width,
            h = that.height,
            scale = w / h;
        w = obj.width || w;
        h = obj.height || (w / scale);
        var quality = 0.7; // 默认图片质量为0.7
        //生成canvas
        var canvas = document.createElement('canvas');
        var ctx = canvas.getContext('2d');
        // 创建属性节点
        var anw = document.createAttribute("width");
        anw.nodeValue = w;
        var anh = document.createAttribute("height");
        anh.nodeValue = h;
        canvas.setAttributeNode(anw);
        canvas.setAttributeNode(anh);
        ctx.drawImage(that, 0, 0, w, h);
        // 图像质量
        if (obj.quality && obj.quality <= 1 && obj.quality > 0) {
            quality = obj.quality;
        }
        // quality值越小，所绘制出的图像越模糊
        var base64 = canvas.toDataURL('image/jpeg', quality);
        // 回调函数返回base64的值
        callback(base64);
    }
}

/**
 * 将以base64的图片url数据转换为Blob
 * @param urlData
 *            用url方式表示的base64图片数据
 */
function convertBase64UrlToBlob(urlData) {
    var arr = urlData.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type: mime});
}

//换掉美图秀秀之后的图片上传
var xhr;
var childrenNum = 5;
var form = new FormData();
var formTwo = new FormData();
var n = 1; //第几个input
var nTwo = 1; //第几个input
var specialN = 60;
var picIdS = new Array();
var picIdSTwo = new Array();

function comppressPic(domId, picId, tag) {
    if (!tag) {
        tag = "div";
    }
    console.log(tag)
    var fileObj = document.getElementById(domId).files[0]; // js 获取文件对象
    var fileName = fileObj.name;
    if (fileObj.size / 1024 > 100) { //大于1M，进行压缩上传
        photoCompress(fileObj, {
            quality: 0.2
        }, function (base64Codes) {
            //console.log("压缩后：" + base.length / 1024 + " " + base);
            //alert(0000)
            var bl = convertBase64UrlToBlob(base64Codes);

            if (form.has("file" + domId)) {
                form.set("file" + domId, bl, fileName); // 文件对象
            } else {
                form.append("file" + domId, bl, fileName); // 文件对象
            }
        }, domId, picId, tag);
    } else { //小于等于1M 原图上传
        console.log(form.has("file" + domId));
        //将文件以Data URL形式读入页面
        var reader = new FileReader();
        reader.readAsDataURL(fileObj);
        reader.onload = function (e) {
            var result = document.getElementById('div' + domId);
            ++n;
            if (picId != "" && picId != undefined && picId != "undefined") {
                picIdS.push(picId);
                var length = $("#" + domId).parent().parent().children().length;
                $("#" + domId).next('img').attr("src", this.result)
                if (form.has("file" + domId)) {
                    form.set("file" + domId, fileObj);
                } else {
                    form.append("file" + domId, fileObj); // 文件对象
                }

            } else {
                if (form.has("file" + domId)) {
                    //alert(000)
                    //console.log(form.has("file"+domId));
                } else {
                    var length = $("#" + domId).parent().parent().children().length;
                    var prediv = $("#" + domId).parent().attr('id')//获取this的上一个div的id
                    //var preNum = prediv.substr(prediv.length-1,1);//截取上一个div的id后面的数字以便于后面元素id的添加
                    //var allDiv = $("#" + domId).parent().siblings();
                    //var parentChil = $("#" + domId).parent().parent().children();
                    var n = $("#" + domId).parent().parent().children().eq(0);//现获取第一个div dom 然后获取所有div中id后面的数字
                    var divNumArr = [];
                    for (let i = 0; n, i < length; n = n.next(), i++) {
                        console.log(n.attr('id'));
                        var id = n.attr('id');
                        divNumArr.push(id.substr(7))
                    }
                    var nextDicName = Math.max.apply(null, divNumArr);
                    console.log(nextDicName)
                    if ($("#" + domId).parent().parent().children().length < childrenNum) {
                        $("#" + domId).parent().parent().append(`
									<${tag} class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${parseInt(nextDicName) + 1}">
									
									<input id="file${parseInt(nextDicName) + 1}" class="divInnerFile" type="file" class="commonAttachId" onchange="comppressPic('file${parseInt(nextDicName) + 1}','${picId}','${tag}')"/>
								</${tag}>
												`)
                    }
                }
                //显示文件
                //result.innerHTML = '<img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />';
                if ($("#div" + domId).find("img").length == 0) {
                    // $("#div"+domId).append('<span class="delIcon" onclick="addDeletePic('+'"file'+domId+'",this)">X</span><img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />')
                    $("#div" + domId).append(`
                            	<span class="delIcon" onclick="addDeletePic('file${domId}',this,1)">X</span><img style="width:50px;height: 50px;" src="${this.result}"/>
                            `);
                } else {
                    $("#div" + domId).find("img").attr('src', this.result);
                    $("#div" + domId).prepend(`<span class="delIcon">X</span>`)
                }
                if (form.has("file" + domId)) {
                    form.set("file" + domId, fileObj);
                } else {
                    form.append("file" + domId, fileObj); // 文件对象
                }

            }

        };

    }
    //检验是否为图像文件
    if (!/image\/\w+/.test(fileObj.type)) {
        alert("看清楚，这个需要图片！");
        return false;
    }


}

function clearform() {
    // console.log(form)
    for (var pair of form.entries()) {
        //console.log(pair[0]+ ', '+ pair[1]);
    }
}

//另一个图片上传
function comppressPicTwo(domId, picId, tag) {
    if (!tag) {
        tag = "div";
    }
    var fileObj = document.getElementById(domId).files[0]; // js 获取文件对象
    var fileName = fileObj.name;
    if (fileObj.size / 1024 > 100) { //大于1M，进行压缩上传
        photoCompressTwo(fileObj, {
            quality: 0.2
        }, function (base64Codes) {
            //console.log("压缩后：" + base.length / 1024 + " " + base);
            var bl = convertBase64UrlToBlob(base64Codes);
            if (formTwo.has("file" + domId)) {
                formTwo.set("file" + domId, bl, fileName); // 文件对象
            } else {
                formTwo.append("file" + domId, bl, fileName); // 文件对象
            }
            //formTwo.append("file", bl, "file_" + Date.parse(new Date()) + ".jpg"); // 文件对象
        }, domId, picId, tag);
    } else { //小于等于1M 原图上传

        //formTwo.append("file", fileObj); // 文件对象
        //将文件以Data URL形式读入页面
        var reader = new FileReader();
        reader.readAsDataURL(fileObj);
        reader.onload = function (e) {
            var result = document.getElementById('div' + domId);
            ++nTwo;
            ++specialN;
            if (picId != "" && picId != undefined && picId != "undefined") {
                picIdSTwo.push(picId)
                var length = $("#" + domId).parent().parent().children().length;
                $("#" + domId).next('img').attr("src", this.result)
                if (formTwo.has("file" + domId)) {
                    formTwo.set("file" + domId, fileObj);
                } else {
                    formTwo.append("file" + domId, fileObj); // 文件对象
                }
                if ($("#" + domId).parent().parent().children().length < childrenNum + 1) {
                    // 	$("#" + domId).parent().parent().append(`
                    // 	<${tag} class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${length+1}">
                    // <input id="file${length+1}" class="divInnerFile" type="file" class="commonAttachId" onchange="comppressPicTwo('file${length+1}','${picId}','${tag}')"/>
                    // </${tag}>
                    // `)
                }
            } else {
                if (formTwo.has("file" + domId)) {
                } else {
                    var length = $("#" + domId).parent().parent().children().length;
                    var prediv = $("#" + domId).parent().attr('id')//获取this的上一个div的id
                    //var preNum = prediv.substr(prediv.length-1,1);//截取上一个div的id后面的数字以便于后面元素id的添加
                    //var allDiv = $("#" + domId).parent().siblings();
                    //var parentChil = $("#" + domId).parent().parent().children();
                    var n = $("#" + domId).parent().parent().children().eq(0);//现获取第一个div dom 然后获取所有div中id后面的数字
                    var divNumArr = [];
                    for (let i = 0; n, i < length; n = n.next(), i++) {
                        console.log(n.attr('id'));
                        var id = n.attr('id');
                        divNumArr.push(id.substr(7))
                    }
                    var nextDicName = Math.max.apply(null, divNumArr);
                    console.log(nextDicName)
                    if ($("#" + domId).parent().parent().children().length < childrenNum) {
                        $("#" + domId).parent().parent().append(`
						<${tag} class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${specialN}">
						<input id="file${specialN}" class="divInnerFile" type="file" class="commonAttachId" onchange="comppressPicTwo('file${specialN}','${picId}','${tag}')"/>
					</${tag}>
					`)
                    }
                }

                //显示文件
                if ($("#div" + domId).find("img").length == 0) {
                    // $("#div"+domId).append('<img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />')
                    $("#div" + domId).append(`
                            	<span class="delIcon" onclick="addDeletePic('file${domId}',this,2)">X</span><img style="width:50px;height: 50px;" src="${this.result}"/>
                            `);
                } else {
                    $("#div" + domId).find("img").attr('src', this.result);
                }
                if (formTwo.has("file" + domId)) {
                    formTwo.set("file" + domId, fileObj);
                } else {
                    formTwo.append("file" + domId, fileObj); // 文件对象
                }
                //result.innerHTML = '<img style="width:50px;height: 50px;"  src="' + this.result + '" alt="" />';
            }

        };
    }
    //检验是否为图像文件
    if (!/image\/\w+/.test(fileObj.type)) {
        alert("看清楚，这个需要图片！");
        return false;
    }

    //console.log(picIdS)
}

function addDeletePic(dom, t, formNum) {
    if (formNum == 1) {
        if (form.has(dom)) {
            form.delete(dom);
        }
    } else {
        if (formTwo.has(dom)) {
            formTwo.delete(dom);
        }
    }


    if ($(t).parent().parent().children().length <= childrenNum) {
        $(t).parent().parent().find("div:last-child").remove('hidden')
    } else {
        $(t).parent().parent().find("div:last-child").addClass('hidden')
    }


    var n = $(t).parent().parent().children().eq(0);//现获取第一个div dom 然后获取所有div中id后面的数字
    var length = $(t).parent().parent().children().length;
    var divNumArr = [];
    for (let i = 0; n, i < length; n = n.next(), i++) {
        console.log(n.attr('id'));
        var id = n.attr('id');
        divNumArr.push(id.substr(7))
    }
    var nextDicName = Math.max.apply(null, divNumArr);
    console.log(nextDicName)
    if ($(t).parent().parent().children().length <= childrenNum) {
        if ($(t).parent().parent().children("div:last-child").find("img").length !== 0) {
            $(t).parent().parent().append(`
            						<div class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${parseInt(nextDicName) + 1}">

            						<input id="file${parseInt(nextDicName) + 1}" class="divInnerFile" type="file" class="commonAttachId" onchange="comppressPic('file${parseInt(nextDicName) + 1}','','div')"/>
            					</div>
            									`)
        }

    }

    $(t).parent().remove()
}

function deletePic(picId, t) {
    picIdS.push(picId);
    // childrenNum++;
    //$(t).parent().css("display", 'none')
    //$(t).parent().addClass('display_none')
    //$(t).parent().addClass('noneNum')
    //$(t).parent().remove()


    if ($(t).parent().parent().children().length <= childrenNum + 1) {
        $(t).parent().parent().find("div:last-child").removeClass('hidden')
    } else {
        $(t).parent().parent().find("div:last-child").addClass('hidden')
    }


    var n = $(t).parent().parent().children().eq(0);//现获取第一个div dom 然后获取所有div中id后面的数字
    var length = $(t).parent().parent().children().length;
    var divNumArr = [];
    for (let i = 0; n, i < length; n = n.next(), i++) {
        console.log(n.attr('id'));
        var id = n.attr('id');
        divNumArr.push(id.substr(7))
    }
    var nextDicName = Math.max.apply(null, divNumArr);
    console.log(nextDicName)
    if ($(t).parent().parent().children().length <= childrenNum) {
        if ($(t).parent().parent().children("div:last-child").find("img").length !== 0) {
            $(t).parent().parent().append(`
            						<div class="file-style" style="background: url(static/img/add_photo.png) -8px" id="divfile${parseInt(nextDicName) + 1}">

            						<input id="file${parseInt(nextDicName) + 1}" class="divInnerFile" type="file" class="commonAttachId" onchange="comppressPic('file${parseInt(nextDicName) + 1}','','div')"/>
            					</div>
            									`)
        }

    }

    $(t).parent().remove()
}

function picUpload(id, attSrc, editId) {
    //console.log(picIdS);
    var picIdsResult = picIdS.join(",");
    var zw = $.getStorage('zw'); //zw.basePath
    var url = zw.basePath + "/common/file/upload?attSrc=" + attSrc + "&ownerId=" + id + "&editId=" + picIdsResult + "&isOnly=0"; // 接收上传文件的后台地址http://zw.un-it.cn/digitalCityWeb
    xhr = new XMLHttpRequest(); // XMLHttpRequest 对象
    xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
    //xhr.onload = uploadComplete; //请求完成
    //xhr.onerror = uploadFailed; //请求失败

    xhr.upload.onloadstart = function () { //上传开始执行方法
        ot = new Date().getTime(); //设置上传开始时间
        oloaded = 0; //设置上传开始时，以上传的文件大小为0
    };

    xhr.send(form); //开始上传，发送form数据
    form = new FormData();
    // for (var key in form) {
    //     form[key].remove();
    // }
    picIdS = [];
    //console.log(form)
}

function picUploadTwo(id, attSrc, editId) {
    //console.log(picIdSTwo);
    var picIdsResult = picIdSTwo.join(",");
    var zw = $.getStorage('zw'); //zw.basePath
    var url = zw.basePath + "/common/file/upload?attSrc=" + attSrc + "&ownerId=" + id + "&editId=" + picIdsResult + "&isOnly=0"; // 接收上传文件的后台地址http://zw.un-it.cn/digitalCityWeb
    xhr = new XMLHttpRequest(); // XMLHttpRequest 对象
    xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
    //xhr.onload = uploadComplete; //请求完成
    //xhr.onerror = uploadFailed; //请求失败

    xhr.upload.onloadstart = function () { //上传开始执行方法
        ot = new Date().getTime(); //设置上传开始时间
        oloaded = 0; //设置上传开始时，以上传的文件大小为0
    };

    xhr.send(formTwo); //开始上传，发送form数据
    formTwo = new FormData();
    // for (var key in form) {
    //     form[key].remove();
    // }
    picIdSTwo = [];
    //console.log(form)
}

// 图片上传
var chgFaceLayer;

function chgFace(id, domId, attSrc, callbackFun) {
    var url = 'html/faceUpload.html?user=' + id + '&domId=' + domId + "&attSrc=" + attSrc;
    if (callbackFun) {
        url = 'html/faceUpload.html?user=' + id + '&domId=' + domId + "&attSrc=" + attSrc + "&callbackFun=" + callbackFun;
    }
    chgFaceLayer = layer.open({
        type: 2,
        area: ['720px', '480px'],
        skin: 'infoBoxLayui',
        title: '修改头像',
        shade: 0.2,
        content: [url, 'no']
    });
}

// 图片上传成功后调用的函数
function chgFaceSuccess(domId, picPath, commonAttachId, callbackFun) {
    var imgSrc = picPath ? picPath : $('#' + domId).attr('src');
    var commonAttachIdObj = $('#' + domId).siblings(".commonAttachId");
    if (commonAttachIdObj.length != 0) {
        if (curr_add.id == null) {
            $('#' + domId).siblings(".commonAttachId").val(commonAttachId);
        }
    }
    $('#' + domId).attr('src', imgSrc + '?tmp=' + Math.random());
    layer.close(chgFaceLayer);
    if (callbackFun) {
        eval(callbackFun + "()");
    }
}

function uploadPhoto(attSrc, id) {
    layer.open({
        type: 2,
        area: ['720px', '480px'],
        skin: 'infoBoxLayui',
        title: '修改头像',
        shade: 0.2,
        content: ['html/photoUpload.html?attSrc=' + attSrc + '&id=' + id]
    });
}


// 列表弹出详细页面
function openFrame(url, title, width, height) {
    if (title == undefined || title == null || title == "") {
        title = "--";
    }
    layer.open({
        type: 2,
        area: [width, height],
        skin: 'infoBoxLayui',
        title: title,
        shade: 0.2,
        success: function (layero, index) {
            currLayFrameIndex = index;
            console.log(currLayFrameIndex)
        },
        content: [url, 'no']
    });
}

function registerVLCEvent(vlc, event, handler) {
    try {
        if (vlc) {
            if (vlc.attachEvent) {
                // Microsoft
                vlc.attachEvent(event, handler);
            } else if (vlc.addEventListener) {
                // Mozilla: DOM level 2
                vlc.addEventListener(event, handler, false);
            }
        }
    } catch (e) {
        alert(e.message)
    }
}

function unregisterVLCEvent(vlc, event, handler) {
    if (vlc) {
        if (vlc.detachEvent) {
            // Microsoft
            vlc.detachEvent(event, handler);
        } else if (vlc.removeEventListener) {
            // Mozilla: DOM level 2
            vlc.removeEventListener(event, handler, false);
        }
    }
}

function cutEvent(domId) {
    try {
        //var vlc = document.getElementById("vlc" + domId);
        //vlc.video.fullscreen = true;
        var obj = $('#vlc' + domId).get(0);
        var width = $(obj).width();
        var height = $(obj).height();
        crop_canvas = document.createElement('canvas');
        crop_canvas.width = width;
        crop_canvas.height = height;
        crop_canvas.getContext('2d').drawImage(obj, 0, 0, width, height, 0, 0, width, height);
        image_base64 = crop_canvas.toDataURL("image/png");
        //vlc.video.takeSnapshot();
        openFrame("html/mapAll/frame_createEvent.html?domId=" + domId, '从视频截图上报事件', '600px', '460px');
        //window.open(image);
    } catch (e) {
        alert(e.message);
    }
}

function makeVLCPlayer(domId, mediaUrl, title) {
    var player = '<div style="height:28px; background:#000; color:#eee;">&nbsp; ' + title + '<span class="close_video" onclick="cutEvent(\'' + domId + '\')" style="right:20px;"><i class="fa fa-photo"></i></span><span class="close_video" data-id="' + domId + '" onclick="close_videoLi($(this).attr(\'data-id\'))"><i class="fa fa-close"></i></span></div>' +
        '<object type="application/x-vlc-plugin" id="vlc' + domId + '" events="True" width="320" height="210" onreadystatechange="alert(\'dddd\')" pluginspage="http://www.videolan.org" codebase="http://downloads.videolan.org/pub/videolan/vlc-webplugins/2.2.2/npapi-vlc-2.2.2.tar.xz">' +
        '<param name="target" value="' + mediaUrl + '" />' +
        '<param name="volume" value="50" />' +
        '<param name="autoplay" value="true" />' +
        '<param name="controls" value="false" />' +
        '<param name="bgcolor" value="#000000" />' +
        '<param name="loop" value="false" />' +
        '<param name="fullscreen" value="true" />' +
        '<embed type="application/x-vlc-plugin" pluginspage="http://www.videolan.org" id="vlc' + domId + '" width="320" height="240" autoplay="yes" fullscreen="true" bgcolor="#000000" controls="false" loop="no" target="' + mediaUrl + '" />' +
        '</object>';
    $('#' + domId).html(player);
    var vlc = document.getElementById("vlc" + domId);
    registerVLCEvent(vlc, "MediaPlayerBuffering", function () {

    });
}

function close_videoLi1(domId) {
    cyberplayer(domId).remove();
}

function play(domId, mediaUrl, txturl) {
    var obj = document.getElementById(domId);
    //console.log(obj.ConnectRTMP);
    if (obj && obj.ConnectRTMP) {
        obj.SetPlayerNum(1);	// 设置播放器只播放一路视频
        //obj.SetPoster(0, "http://16.18.251.245:1935/cgi-bin/snapshot.cgi?chn=0&u=admin&p=admin123&q=0&d=1&rand=" + Math.random());	// 在rtmp未连接成功时设置封面图
        obj.Login("admin", "admin123", "");	// rtmp登陆认证
        obj.ConnectRTMP(0, mediaUrl, txturl);	// 连接rtmp服务，第一个参数是服务器地址，第二个参数是通道编号和编码方式，“ch0”就是第一个通道，“ch1”就是第二个通道，后面的“_1.264”保持不变
    } else {
        // 避免flash初始化ConnectRTMP失败，因此需要再失败时重试
        setTimeout(function () {
            play(domId, mediaUrl, txturl);
        }, 1000);
        console.log(txturl)
    }
}

function cutPicByVideo(domId, mediaUrl, txturl, lat, lng, title, channerNum) {
    //http://zhcg.erqi.gov.cn:10800/api/v1/getsnap?channel=39
    // alert(0000)
    //players[domId].setFullscreen(false);
    if (document.webkitCancelFullScreen) {
        document.webkitCancelFullScreen();
    }
    //var file = "http://"+txturl+"/api/v1/getsnap?channel=" + channerNum;
    var file = "http://" + txturl + "/api/v1/getsnap?channel=" + channerNum;
    //loading = layer.load(0, { shade: [0.2, '#aaa'] });
    getBase64(file).then(function (base64) {
        console.log(base64);
        image_base64 = base64;
        videos_array[domId] = {
            data: {
                title: title,
                lat: lat,
                lng: lng
            }
        };
        layer.close(loading);
        openFrame("html/mapAll/frame_createEvent.html?domId=" + domId + "&id=" + curr_Marker.data.id, '从视频截图上报事件', '600px', '700px');
    }, function (err) {
        layer.close(loading);
        layer.msg('获取截图失败！稍后重试！');
        console.log(err);
    });
}

function destoryVideo(domId) {
    players[domId].dispose()
    console.log(domId)
    clearInterval(domIdNums[domId])
}

var domIdNums;
var players;
var vars = {};

function showPlayer(domId, mediaUrl, lat, lng, title, txturl, channerNum, domN) {
    if (mediaUrl == 'ondemand') {//判断是否是按需播放标识
        //loading = layer.load(0, { shade: [0.2, '#aaa'] });
        layer.msg('正在打开.....');
        sendGET('/api/v1/getchannelstream?Channel=' + channerNum + '&Protocol=HLS&Line=local&From=lan', '', function (d) {
            //layer.close(loading);
            console.log(d.EasyDarwin.Body.URL)
            var videoAddress = d.EasyDarwin.Body.URL;
            if ($('.' + domId).length < 1) {

                $("#video_players").append(`
												<video id="video${domN}" style="float: left;position:relative;" class="${domId} video-js vjs-default-skin vjs-big-play-centered" ><p class="vjs-no-js">您的浏览器不支持H5或FLASH</p></video>
					
                            `);
                try {
                    var tryNumber = 0;
                    tryAgin();

                    function tryAgin() {
                        console.log('有请求一下')
                        $.ajax({
                            url: 'http://' + txturl + '/api/v1/getchannelstream?Channel=' + channerNum + '&Protocol=HLS&Line=local&From=lan',
                            success: function (res) {
                                $.ajax({
                                    url: 'http://' + txturl + '/hls/stream_' + channerNum + '/stream_' + channerNum + '_live.m3u8',
                                    success: function (res) {
                                        setTimeout(function () {
                                            var player = videojs('video' + domN, {
                                                width: 400,
                                                height: 300,
                                                controls: true,
                                                preload: "true",    //预加载：string；'auto'|'true'|'metadata'|'none'
                                                //poster:'source/suoluetu.jpg',//预览图：string
                                                muted: false, //静音
                                                inactivityTimeout: 0,
                                                error: function () {
                                                    layer.msg('请求资源出错！请关闭重试！');
                                                },
                                                sources: [
                                                    {
                                                        src: 'http://' + txturl + '/hls/stream_' + channerNum + '/stream_' + channerNum + '_live.m3u8',
//                        src:'http://zhcg.erqi.gov.cn:10801/hls/stream_1/stream_1_live.m3u8',
                                                        type: 'application/x-mpegURL'
                                                    }
                                                ],
                                                controlBar: {
                                                    muteToggle: false, //静音按钮
                                                    volumeMenuButton: false, // 音量调节

                                                }
                                            }, function onPlayerReady() {
                                                layer.msg('请求成功！');
                                                console.log(this);//video1_html5_api
                                                $('#video' + domN).append(`<button class="btn-primary" onclick="cutPicByVideo('${domId}','${mediaUrl}','${txturl}','${lat}','${lng}','${title}','${channerNum}')" style="background-color: rgba(2, 131, 204, 0.39);position: absolute;left: 2%;top: 16px;z-index: 1000000000">截图</button>` +
                                                    '<span onclick="destoryVideo(\'' + domId + '\')" style="position: absolute;cursor:pointer;right: 5%;top: 14px;color: #925959;z-index: 100000">X</span>');
                                                //$('#video'+n).append('<span onclick="destoryVideo('+n+')" style="position: relative;right: -75%;color: #925959;">X</span>');
                                                this.on('error ', function () {
                                                    layer.msg('请求资源出错！请关闭重试！');
                                                });
                                                //this.play();
                                                //videoMap['video'+n] = player;

                                                var domIdNum = self.setInterval(function () {
                                                    $.ajax({
                                                        url: 'http://' + txturl + '/api/v1/touchchannelstream?Channel=' + channerNum + '&Protocol=HLS&Line=local&From=lan',
                                                        dataType: 'json',
                                                        type: 'get',
                                                        async: false,
                                                        data: {},
                                                        //jsonp: 'callback',
                                                        success: function (dd) {
                                                            console.log(dd);
                                                        }
                                                    })
                                                }, 45000);
                                                if (!domIdNums) {
                                                    domIdNums = {};
                                                }
                                                if (!players) {
                                                    players = {};
                                                }
                                                domIdNums[domId] = domIdNum;
                                                players[domId] = player;
                                                //this.enterFullScreen();
                                            });
                                        }, 1000)
                                    },
                                    error: function (e) {
                                        console.log(e);
                                        layer.msg('请求中');
                                        if (tryNumber < 30) {
                                            setTimeout(tryAgin, 500)
                                            //tryAgin();
                                        } else {
                                            layer.msg('请求异常');
                                        }
                                        tryNumber++;
                                    }
                                })
                            },
                            error: function (e) {
                                console.log(e);
                                layer.msg('获取通道信息失败！');
                                // if(tryNumber<50){
                                //     setTimeout(tryAgin,500)
                                //     //tryAgin();
                                // }else{
                                //     layer.msg('请求异常');
                                // }
                                // tryNumber++;
                            }
                        })
                    }


                } catch (err) {
                    alert("获取视频失败！！");
                }

            } else {
                layer.msg("已经打开了！");
            }
        }, {baseURL: 'http://' + txturl}, function (error) {
            console.log(error);
        });
    } else {
        layer.msg('非按需播放视频！')
    }
    // if(mediaUrl.indexOf("rtmp")==0){
    //     if($('#' + domId).length < 1) {
    //         $('ul#video_players').append(`
    // 	<object id="${domId}" type="application/x-shockwave-flash" data="JaViewer.swf" style="outline: none medium; height: 240px; width: 320px; border:solid 1px red" height="100%;" width="100%">
    // 		<param name="movie" value="JaViewer.swf">
    // 		<param name="allowFullScreen" value="true">
    // 		<param name="allowScriptAccess" value="always">
    // 	</object>
    // 	<span style="color: #da6e6e;" class="close_video"  onclick="close_videoLi('${domId}')"><i class="fa fa-close"></i></span>
    //     `);
    //         play(domId,mediaUrl,txturl);
    // }
    //
    // }
    // if($('#' + domId).length < 1) {
    // $('ul#video_players').append('<div id="' + domId + '" class="video_playerLi_' + domId + '" style="background:url(/static/img/loadingV.gif) no-repeat center;width:320px;height:240px">Loading...</div>');
    // $('#' + domId).removeClass('bounceIn animated').addClass('bounceIn animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function() {
    // 	$(this).removeClass("bounceIn animated")
    // });
    // //setTimeout(function() {
    // 	if(mediaUrl.substr(0, 4) == 'rtsp') {
    // 		makeVLCPlayer(domId, mediaUrl, title);
    // 	} else {
    // 		var player = cyberplayer(domId).setup({
    // 			width: 320,
    // 			height: 240,
    // 			isLive: true, // 必须设置，表明是直播视频
    // 			file: mediaUrl, // <—hls直播地址
    // 			autostart: true,
    // 			stretching: "uniform",
    // 			volume: 100,
    // 			controls: true,
    // 			rightclick: [{
    // 				title: "截图上报",
    // 				link: "javascript:videoCutUpload('" + domId + "');" // 跳转链接
    // 			}],
    // 			hls: {
    // 				reconnecttime: 5
    // 			},
    // 			ak: "7e390f0528f24d5db53effa824d7edea"
    // 		});
    // 		player.data = {
    // 			lat: lat,
    // 			lng: lng,
    // 			title: title
    // 		}
    // 		player.onReady(function(e) {
    // 			videos_array[domId] = player;
    // 			$('#' + domId).append('<span class="close_video" data-id="' + domId + '" onclick="close_videoLi($(this).attr(\'data-id\'))"><i class="fa fa-close"></i></span>');
    // 		});
    // 	}
    // //}, 100)
    // } else {
    // $('#' + domId).removeClass('bounceIn animated').addClass('bounceIn animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function() {
    // 	$(this).removeClass("bounceIn animated")
    // });
    // }
}

function close_videoLi(domId, isondemand, t) {//isondemand是否是按需播放的监控视频
    console.log(domId)
    if (isondemand) {
        console.log(domIdNums[domId])
        clearInterval(domIdNums[domId])
    }
    var n = $(t).parent().attr('id').substring(5, $(t).parent().attr('id').length);
    $(t).parent().parent().remove();
    //cyberplayer(domId).remove();
    //$('#' + domId).remove();
    //players[domId].remove();
    //  console.log(domId + "=============");
    // // $('#' + domId).remove();
    // $('#' + domId).removeClass('bounceOut animated').addClass('bounceOut animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function() {
    //
    // 	try {
    // 		var vlc = document.getElementById("vlc" + domId);
    // 		vlc.playlist.stop();
    // 	} catch(e) {}
    // 	try {
    // 		//cyberplayer(domId).remove();
    // 		//players[domId].remove();
    //          $('#' + domId).remove();
    // 	} catch(e) {}
    // });
}

function closeAll_videoLi() {
    var ids = [];
    //console.log($(".close_video"));
    var objs = $(".close_video");
    for (var i = 0; i < objs.length; i++) {
        var id = $(objs[i]).attr("data-id");
        $('#' + id).removeClass('bounceOut animated').addClass('bounceOut animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function () {
            $(this).remove();
            $(".video_playerLi_" + id).remove();
            cyberplayer(id).remove();
        });
    }
}

// 数组去重
function distinctArr(arr) {
    len = arr.length;
    arr.sort(function (a, b) { //对数组进行排序才能方便比较
        return a - b;
    })

    function loop(index) {
        if (index >= 1) {
            if (arr[index] === arr[index - 1]) {
                arr.splice(index, 1);
            }
            loop(index - 1); //递归loop函数进行去重
        }
    }

    loop(len - 1);
    return arr;
}

/*Array.prototype.distinctArr = function() {
	var arr = this,
		len = arr.length;
	arr.sort(function(a, b) { //对数组进行排序才能方便比较
		return a - b;
	})

	function loop(index) {
		if(index >= 1) {
			if(arr[index] === arr[index - 1]) {
				arr.splice(index, 1);
			}
			loop(index - 1); //递归loop函数进行去重
		}
	}
	loop(len - 1);
	return arr;
};
*/
function showCarplayer(domId, txturl, domN, domIdNum) {
    //layer.msg('正在打开.....');
    if ($('.' + domId).length < 1) {
        var video_html = '<video id="video_' + domN + '" style="float: left;position:relative;" class="' + domN + ' video-js vjs-default-skin vjs-big-play-centered" >' +
            '<p class="vjs-no-js">您的浏览器不支持H5或FLASH</p>' +
            '</video>'
        $("#video_players").append(video_html)
        try {
            var tryNumber = 0;
            console.log('有请求一下')
            console.log(txturl)
            var player = videojs('video_' + domN, {
                    width: 400,
                    height: 300,
                    controls: true,
                    preload: "true",//预加载：string；'auto'|'true'|'metadata'|'none'
                    //poster:'source/suoluetu.jpg',//预览图：string
                    muted: false, //静音
                    inactivityTimeout: 0,
                    error: function () {
                        layer.msg('请求资源出错！请关闭重试！');
                    },
                    sources: [
                        {
                            src: txturl,
                            type: 'application/x-mpegURL'
                        }
                    ],
                    controlBar: {
                        muteToggle: false, //静音按钮
                        volumeMenuButton: false, // 音量调节

                    }
                }, function onPlayerReady() {
                    layer.msg('请求成功！');
                    console.log(this);//video1_html5_api
//                                              $('#video_'+domN).append(`<button class="btn-primary" onclick="cutPicByVideo('${domId}','${txturl}')" style="background-color: rgba(2, 131, 204, 0.39);position: absolute;left: 2%;top: 16px;z-index: 1000000000">截图</button>` +
//                                               '<span onclick="destoryVideo(\''+domId+'\')" style="position: absolute;cursor:pointer;right: 5%;top: 14px;color: #925959;z-index: 100000">X</span>');
                    $('#video_' + domN).append('<span onclick="destoryVideo(\'' + domId + '\')" style="position: absolute;cursor:pointer;right: 5%;top: 14px;color: #925959;z-index: 100000">X</span>');
                    this.on('error ', function () {
                        layer.msg('请求资源出错！请关闭重试！');
                    });

                    if (!domIdNums) {
                        domIdNums = {};
                    }
                    if (!players) {
                        players = {};
                    }
                    domIdNums[domId] = domIdNum;
                    players[domId] = player;
                    this.enterFullScreen();
                }
            );
        } catch (err) {
            alert("获取视频失败！！");
        }

    } else {
        layer.msg("已经打开了！");
    }


}

function distinctArr(arr) {

}

function setDatepicker(dateDoms) {
    if ($(dateDoms) != undefined) {
        $.each($(dateDoms), function (i, item) {
            $(item).datepicker("setDate", $(item).val());
        });
    } else {
        console.warn("setDatepicker时传入参数为undefined!");
    }
}

/**
 * 获得图形的中心点
 */
function getCenterPoint(path) {
    var x = 0.0;
    var y = 0.0;
    for (var i = 0; i < path.length; i++) {
        x = x + parseFloat(path[i].lng);
        y = y + parseFloat(path[i].lat);
    }
    x = x / path.length;
    y = y / path.length;

    return new BMap.Point(x, y);
}

/**
 * 通过百度地图坐标转GEOJSON
 */
function getGEOJSON(points) {
    var geo = [];
    for (var i = 0; i <= points.length; i++) {
        var point = [];
        var lng = i == points.length ? points[0].lng : points[i].lng;
        var lat = i == points.length ? points[0].lat : points[i].lat;
        point.push(lng);
        point.push(lat);
        geo.push(point);
    }
    var geo_json = [];
    geo_json.push(geo);
    return geo_json;
}

function getPolygonCenterPoint(points) {
    var polygonPath = getGEOJSON(points);
    var polygon = turf.polygon(polygonPath);
    var pointOnPolygon = turf.pointOnFeature(polygon);
    var centerPoint = new BMap.Point(pointOnPolygon.geometry.coordinates[0], pointOnPolygon.geometry.coordinates[1]);
    return centerPoint;
}

function setSwitchery(switchElement, checkedBool) {
    if ((checkedBool && !switchElement.isChecked()) || (!checkedBool && switchElement.isChecked())) {
        switchElement.setPosition(true);
        switchElement.handleOnchange(true);
    }
}

/*var showDetailFlag = $.getStorage('showDetailFlag') == 'true';*/

function toggleShowDetail(flag) {
    console.log(flag);
    $.setStorage('showDetailFlag', flag);
}

function getCommonAttachId(commonAttachId) {
    var commonAttachIdObj = $(".commonAttachId");
    var commonAttachId = "";
    for (var i = 0; i < commonAttachIdObj.length; i++) {
        if ($(commonAttachIdObj[i]).val() != "") {
            commonAttachId = commonAttachId + "," + $(commonAttachIdObj[i]).val();
        }
    }
    commonAttachId = commonAttachId.substring(1, commonAttachId.length);
    return commonAttachId;
}

function simplifyValue(value, num) {
    if (!num) {
        num = 10;
    }
    if (value && value.length > num) {
        return value.substring(0, num) + "...";
    } else {
        return value;
    }
}

function foucsNext(currObj, callbackFun) {

}

// 统一拦截ajax请求
//var user = $.getStorage("user");
//if(user && user.id) {
//	$.ajaxSetup({
//		//dataType: 'jsonp',
//		beforeSend: function() {
//			var params = arguments[1].url;
//			if(arguments[1].url.indexOf("?") > -1) {
//				arguments[1].url = arguments[1].url + "&currentUserId=" + transUndefined(user.id) + "&ownStreetCode=" + transUndefined(user.ownStreetCode);
//			} else {
//				arguments[1].url = arguments[1].url + "?" + "&currentUserId=" + transUndefined(user.id) + "&ownStreetCode=" + transUndefined(user.ownStreetCode);
//			}
//		},
//		complete: function(XMLHttpRequest, status) {
//
//		}
//	});
//}

$(function () {
    // 初始化按钮权限
    var user = $.getStorage("user");
    $('.menuCtl').each(function () {
        var menuCtl = $(this).attr('data-menu');
        $(this).find('.authMenu').each(function () {
            var authMenu = $(this);
            for (var i = 0; i < user.menuList.length; i++) {
                var menuList = user.menuList[i];
                if (menuList.remarks == menuCtl && menuList.treeLevel == 1) {
                    for (var t = 0; t < menuList.childList.length; t++) {
                        var menuListChildren = menuList.childList[t];
                        if (menuListChildren.remarks == authMenu.attr('data-menu')) {
                            authMenu.removeClass('disabled').addClass('enabled').css('-webkit-filter', 'grayscale(0)').css('filter', 'grayscale(0)');
                            authMenu.removeClass("preventDefault");
                            authMenu.removeClass("preventClick");
                            authMenu.removeClass("hide");
                        }
                    }
                }
            }
        });
    });
    $('.disabled').click(function () {
        layer.msg('很抱歉，您无权操作');
    });

    $('.hide').click(function () {
        $(this).addcss("display", "none");
    });

    $(".preventDefault").click(function () {
        event.preventDefault();
    });
    $(".preventClick").attr("onclick", "event.stopPropagation();");
    //
    var showDetailFlag = $.getStorage('showDetailFlag');
    /*console.log(showDetailFlag + "**");
    if(showDetailFlag == null || typeof(showDetailFlag) == 'undefined' || showDetailFlag == '') {
        toggleShowDetail(true);
    }*/
    //showDetailFlag = showDetailFlag == 'true';
    if (switcherys.length > 0) {
        setSwitchery(switcherys[3], showDetailFlag);
    }
});

function exportExcel(url) {
    layer.msg("正在导出，请耐心等待，不要重复点击");
    if ($("#exportExcel").length > 0) {
        //$('#exportExcel').remove();
        $("#exportExcel").attr("src", url);
    } else {
        //loading = layer.load(0, { shade: [0.2, '#aaa'] });
        $("<iframe src='" + url + "' width='100' height='100' id='exportExcel' name='exportExcel' style='position:absolute;left:-10px;' frameborder='no' marginheight='0' marginwidth='0' allowTransparency='true'></iframe>").prependTo('body');
    }
}

function timestampToTime(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y + M + D + h + m + s;
}

/*模板 begin*/
function foreachAppendToDom(_array, temId, domId) {
    for (var j = 0; j < _array.length; j++) {
        var htmlStr = $("#" + temId).html().toString();
        var val = htmlStr.match(/[^\{\}]+(?=\})/g);
        for (var i = 0; i < val.length; i++) {
            if (_array[j][val[i]] && _array[j][val[i]] != null) {
                htmlStr = htmlStr.replace(new RegExp("\\${" + val[i] + "}"), _array[j][val[i]]);
            } else {
                htmlStr = htmlStr.replace(new RegExp("\\${" + val[i] + "}"), "");
            }
        }
        var html = $("#" + domId).html().toString();
        $("#" + domId).html(html + htmlStr);
    }
}

function replaceTemplate(data, temId) {
    var htmlStr = $("#" + temId).html().toString();
    var val = htmlStr.match(/[^\{\}]+(?=\})/g);
    for (var i = 0; i < val.length; i++) {
        if (data && data != null) {
            htmlStr = htmlStr.replace(new RegExp("\\${" + val[i] + "}"), data);
        } else {
            htmlStr = htmlStr.replace(new RegExp("\\${" + val[i] + "}"), "");
        }
    }
    return htmlStr;
}

/*模板 end*/

//图片转64
//传入图片路径，返回base64
function getBase64(img) {
    function getBase64Image(img, width, height) { //width、height调用时传入具体像素值，控制大小 ,不传则默认图像大小
        var canvas = document.createElement("canvas");
        canvas.width = width ? width : img.width;
        canvas.height = height ? height : img.height;

        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
        var dataURL = canvas.toDataURL();
        return dataURL;
    }

    var image = new Image();
    image.crossOrigin = '';
    image.src = img;
    var deferred = $.Deferred();
    if (img) {
        image.onload = function () {
            deferred.resolve(getBase64Image(image)); //将base64传给done上传处理
        }
        return deferred.promise(); //问题要让onload完成后再return sessionStorage['imgTest']
    } else {
        layer.msg('截图失败！');
    }
}

//根据身份证获取出生年月日
function getBirthdayFromIdCard(idCard) {
    var birthday = "";
    if (idCard != null && idCard != "") {
        if (idCard.length == 15) {
            birthday = "19" + idCard.substr(6, 6);
        } else if (idCard.length == 18) {
            birthday = idCard.substr(6, 8);
        }

        birthday = birthday.replace(/(.{4})(.{2})/, "$1-$2-");
    }

    return birthday;
}

//点击设置select的title
function titleChange(id) {
    $("#" + id).attr("title", $('#' + id + ' option:selected').text())
}

//下载表格
function downGridLogsNumData(url, arg) {
    layer.confirm("是否下载报表？", {
            btn: ['确定', '取消'] //按钮
        },
        function (index) {
            layer.close(index);
            layer.msg("正在下载请勿重复点击！");
            location.href = zw.basePath + url + "?" + arg;
        });
}

// toJSON和fromJSON合并，重复的值toJSON会覆盖fromJSON
function extendJSON(fromJSON, toJSON) {
    if (fromJSON && toJSON) {
        $.each(toJSON, function (i, item) {
            fromJSON[i] = item;
        });
    }
}

