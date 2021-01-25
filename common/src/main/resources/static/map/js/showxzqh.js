var officePolygon, officePolygon = [];
var bound_color = new Array('#3366CC', '#DC3912', '#FF9900', '#109618',
    '#990099', '#0099C6', '#DD4477', '#66AA00', '#B82E2E', '#F26722',
    '#DB9327', '#F8D70C', '#5C84C2', '#A9C8E4', '#737574', '#6BC1C0',
    '#BB4225', '#AAACAB', '#83C75D', '#00AE72', '#00B2BF', '#426EB4',
    '#514696', '#79378B', '#E54646', '#F09C42', '#FCF54C', '#F9CC76',
    '#F19373', '#C57CAC', '#635BA2', '#6EC3C9', '#D59B00', '#EC870E',
    '#C50023', '#8F006D', '#52096C', '#00A6AD', '#3366CC', '#DC3912',
    '#FF9900', '#109618', '#990099', '#0099C6', '#DD4477', '#66AA00',
    '#B82E2E', '#F26722', '#DB9327', '#F8D70C', '#5C84C2', '#A9C8E4',
    '#737574', '#6BC1C0', '#BB4225', '#AAACAB', '#83C75D', '#00AE72',
    '#00B2BF', '#426EB4', '#514696', '#79378B', '#E54646', '#F09C42',
    '#FCF54C', '#F9CC76', '#F19373', '#C57CAC', '#635BA2', '#6EC3C9',
    '#D59B00', '#EC870E', '#C50023', '#8F006D', '#52096C', '#00A6AD');
var marker_color = new Array('#888888', '#169C45', '#1296DB', '#FF9900',
    '#D81E06', '#945305', '#ffffff');

function initPolygon(_data, centerPoint, map) {
    var boundaries = _data;
    boundaries = transBoundaries(JSON.stringify(boundaries));
    console.log(boundaries);
    if (boundaries != null && boundaries != "" && boundaries != null) {
        for (var t = 0; t < boundaries.coordinate.length; t++) {
            var ply = new BMap.Polygon(boundaries.coordinate[t], getPlyOpt(0)); // 建立多边形覆盖物
            ply.level = 5;
            ply.area = 0;
            ply.centerPoint = getPolygonCenterPoint(ply.getPath());
            officePolygon.push(ply);
        }
    }
    showXZQH(map, officePolygon, centerPoint);
}

function showXZQH(map, officePolygon, centerPoint) {
    var curr_Level = map.getZoom();
    if (officePolygon) {
        var polygons = officePolygon;
        for (var i = 0; i < polygons.length; i++) {
            map.addOverlay(polygons[i]);
            var _position;
            if (centerPoint && centerPoint.trim() != "") {
                var centerpoint = centerPoint.split(",");
                var _longitude = centerpoint[0];
                var _latitude = centerpoint[1];
                _position = new BMap.Point(_longitude, _latitude);
            } else {
                _position = polygons[i].centerPoint;
            }
        }
    }
}

function transBoundaries(coordinate) {
    var coords = JSON.parse(coordinate.replace(/\\/g, ''));
    coords.coordinate = [];
    if (coords.type == "MultiPolygon") {
        for (var i = 0; i < coords.coordinates.length; i++) {
            var points = [];
            for (var t = 1; t < coords.coordinates[i][0].length; t++) {
                points.push(new BMap.Point(coords.coordinates[i][0][t][0],
                    coords.coordinates[i][0][t][1]));
            }
            coords.coordinate.push(points);
        }
    } else {
        var points = [];
        for (var t = 1; t < coords.coordinates[0].length; t++) {
            points.push(new BMap.Point(coords.coordinates[0][t][0],
                coords.coordinates[0][t][1]));
        }
        coords.coordinate.push(points);
    }
    return coords;
}

function getRealPlyOpt(i) {
    var plyOptions = {
        strokeWeight: 2,
        strokeColor: '#e74a25',
        strokeOpacity: 0.5,
        strokeStyle: 'dashed',
        fillOpacity: 0.2
        // fillColor: bound_color[i]
    };
    return plyOptions;
}

function getMarkerLabelStyleOption(color, fontSize, fontWeight, selfControl) {
    var display = "inline";
    if (selfControl == undefined || selfControl) {
        if ($.getStorage("markerLableFlag") == "false") {
            display = "none";
        }
    }
    var o = {};
    o.border = 'none';
    o.background = 'none';
    o.color = color;
    o.fontSize = fontSize;
    o.textShadow = '1px 1px 1px #fff';
    if (fontWeight != null) {
        o.fontWeight = fontWeight;
    }
    o.fontFamily = '"Microsoft YaHei",SimHei,"PingFang SC","Hiragino Sans GB"';
    o.display = display;
    return o;
}

function clearAllMarker(type, map) {
    $.each(overlays, function (n, v) {
        if (type != '' && type != n) {
            null;
        } else {
            for (var i = 0; i < v.length; i++) {
                map.removeOverlay(v[i]);
                if (v[i].type != 'xzqh' && v[i].type != 'xzqhLabel'
                    && v[i].type != 'realXzqh'
                    && v[i].type != 'realXzqhLabel'
                    && v[i].type != undefined) {
                    if ($.inArray(v[i], markers) > -1) {
                        markers.splice($.inArray(v[i], markers), 1); // 从数组中删除值为v[i]的元素
                        v[i]['del'] = true;
                    }
                }
            }
        }
    });
}

function getPlyOpt(i) {
    var j = i;
    if (i > bound_color.length) {
        j = Math.ceil(Math.random() * (bound_color.length - 1));
        if (j < 0) {
            j = 0;
        }
    }
    var plyOptions = {
        strokeWeight: 0.1,
        strokeColor: '#ffffff',
        strokeOpacity: 0.3,
        strokeStyle: 'solid',
        fillOpacity: 0.2,
        fillColor: bound_color[j]
    };
    return plyOptions;
}