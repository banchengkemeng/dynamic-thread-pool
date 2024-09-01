import {getSettings} from "@/api/settings";
import requests from "@/helper/requests";

// 获取配置, 修改全局前缀
getSettings().then(res => {
    const contextPath = res.data.data.apiContextPath
    requests.defaults.baseURL += contextPath
    window['grafanaDashboardUrl'] = res.data.data.grafanaDashboardUrl
})