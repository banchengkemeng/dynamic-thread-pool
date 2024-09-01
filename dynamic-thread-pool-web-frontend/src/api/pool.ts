import {AxiosRequestConfig, AxiosResponse} from "axios";
import requests, {BaseResponse} from "@/helper/requests";
import {UpdatePoolConfigType} from "@/componets/UpdatePoolConfigForm";

export interface ThreadPoolConfigType {
    applicationName: string;
    threadPoolName: string;
    corePoolSize: number;
    maximumPoolSize: number;
    activeThreadCount: number;
    poolSize: number;
    queueType: string;
    queueSize: number;
    remainingCapacity: number;
}

type GetThreadPoolConfigListResponse = BaseResponse<ThreadPoolConfigType[]>
export async function getThreadPoolConfigList(): Promise<AxiosResponse<GetThreadPoolConfigListResponse>> {
    return requests({
        method: 'get',
        url: '/pool/list'
    } as AxiosRequestConfig)
}

type RefreshThreadPoolConfigListResponse = BaseResponse<null>
export async function refreshThreadPoolConfigList(): Promise<AxiosResponse<RefreshThreadPoolConfigListResponse>> {
    return requests({
        method: 'get',
        url: '/pool/refresh'
    } as AxiosRequestConfig)
}

export async function updateThreadPoolConfig(data: UpdatePoolConfigType): Promise<AxiosResponse<BaseResponse<boolean>>>{
    return requests({
        method: 'post',
        url: '/pool/update',
        data
    } as AxiosRequestConfig)
}