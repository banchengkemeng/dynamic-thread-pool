import requests, {BaseResponse} from "../helper/requests";
import {AxiosRequestConfig, AxiosResponse} from "axios";

type SettingsResponse = BaseResponse<{
    apiContextPath: string;
    grafanaDashboardUrl: string;
}>

export async function getSettings(): Promise<AxiosResponse<SettingsResponse>> {
    return requests({
        url: '/settingsLZQGJPUFNS',
        method: 'get'
    } as AxiosRequestConfig)
}