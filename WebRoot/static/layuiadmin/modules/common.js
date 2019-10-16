/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define([], function (exports) {
    var commonUtils = {
        parseJson2Params: function (data) {
            var params = "";
            for (var p in data) {
                params += p + "=" + encodeURIComponent(data[p]) + "&";
            }
            if (params.length > 1) {
                params = params.substr(0, params.length - 1);
            }
            return params;
        },
        redirectTo: function (pagePath, data){
            var url = pagePath
            if (data) {
                var paramsString = this.parseJson2Params(data);
                if (url.indexOf("?") == -1) {
                    url += "?" + paramsString;
                } else {
                    url += "&" + paramsString;
                }
            }
            location.href = url;
        }
    };
    exports("common", commonUtils);
});