import requests, {BaseResponse} from "@/helper/requests";
import {AxiosRequestConfig, AxiosResponse} from "axios";

interface LoginParameter {
    username: string;
    password: string;
}

type LoginResponse = BaseResponse<{
    authToken: string;
}>

export async function login(data: LoginParameter): Promise<AxiosResponse<LoginResponse>> {
    return requests({
        method: 'post',
        url: '/auth/login',
        data
    } as AxiosRequestConfig)
}


type CheckAuthResponse = BaseResponse<{
    isLogin: boolean;
}>

export async function checkAuth(): Promise<AxiosResponse<CheckAuthResponse>> {
    return requests({
        method: 'get',
        url: '/auth/check'
    } as AxiosRequestConfig)
}