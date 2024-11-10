/**
 * @Description:
 * @author yu
 * @date 2022/2/27 17:54
 */
/**
 * 获取当前项目路径
 * @function getURL
 * @static
 * @returns {string} 如：http://www.abc.com/EmptyProject
 */
function getURL() {
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： cis/website/meun.htm
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName); //获取主机地址，如： http://localhost:8080
    var localhostPaht = curWwwPath.substring(0, pos); //获取带"/"的项目名，如：/cis
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    var rootPath = localhostPaht + projectName;
    return rootPath;
}

var user = {};

function getUser() {
    $.ajax({
        type: "post",
        url: getURL() + "/user",
        data: {},
        dataType: "json",
        async: false,
        success: function (data) {
            if (data.status) {
                user = data.data;
            }
        }
    });
}

//获取url传来的参数
function getQueryString(parameter){
    var reg = new RegExp("(^|&)"+ parameter +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r != null){
        return  decodeURI(r[2]);
    }
    return null;
}