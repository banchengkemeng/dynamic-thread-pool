import axios, {AxiosInstance, AxiosRequestConfig, AxiosResponse} from "axios";
import {getValueFromStore, STORE_KEY} from "./store";

const requests: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8091',
    withCredentials: true,
    timeout: 1000 * 10
});

// 添加请求拦截器
requests.interceptors.request.use(function (config) {
    if (!config.url.includes('login') || !config.url.includes('settings') ) {
        config.headers.set("Auth-Token", getValueFromStore(STORE_KEY.AUTH_TOKEN))
    }
    // 在发送请求之前做些什么
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
requests.interceptors.response.use(function (response: AxiosResponse<BaseResponse<any>>) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});

export default requests;

export interface BaseResponse<T> {
    status: number;
    message: string;
    description: string;
    data: T
}