import ThreadPoolList, {ThreadPoolListItem} from "@/componets/ThreadPoolList";
import WithAuth from "@/componets/WithAuth";
import {useEffect, useState} from "react";
import {getThreadPoolConfigList, refreshThreadPoolConfigList, updateThreadPoolConfig} from "@/api/pool";
import {message} from "antd";
import {ProCard} from "@ant-design/pro-components";

const ThreadPoolsPage = () => {
    const [messageApi, contextHolder] = message.useMessage();
    const [loading, setLoading] = useState(true)
    const [poolList, setPoolList] = useState([] as ThreadPoolListItem[])

    const loadData = () => {
        getThreadPoolConfigList()
            .then(res => {
                const unwrap = res.data
                if (unwrap.status !== 200) {
                    messageApi.error(
                        `${unwrap.message}:${unwrap.description}`
                    )
                    setLoading(false)
                    return;
                }
                setPoolList(res.data.data.map<ThreadPoolListItem>((item, index) => {
                    return {
                        key: index,
                        ...item
                    }
                }))
                setLoading(false)
            })
            .catch(err => {
                messageApi.error(err.message)
                setLoading(false)
            })
    }

    const onRefresh = () => {
        refreshThreadPoolConfigList().then(() => {
            loadData()
        })
    }

    // 修改线程池配置接口
    const updatePoolConfig = async (formValue) => {
        let result = false
        await updateThreadPoolConfig(formValue)
            .then(res => {
                const unwrap = res.data
                if (unwrap.status !== 200) {
                    messageApi.error(
                        `${unwrap.message}:${unwrap.description}`
                    )
                    return;
                }

                if (!unwrap.data) {
                    messageApi.error(
                        `${unwrap.message}:${unwrap.description}`
                    )
                    return;
                }

                messageApi.success(unwrap.description)
                result = true;
                loadData()
            })
            .catch(err => {
                messageApi.error(err.message)
            })
        return result
    }

    useEffect(() => {
        loadData()
    }, []);

    return (
        <>
            {contextHolder}
            <ProCard bordered>
                <ThreadPoolList
                    loading={loading}
                    threadPoolList={poolList}
                    onRefresh={onRefresh}
                    onUpdateThreadPoolConfig={updatePoolConfig}
                />
            </ProCard>
        </>
    )
}

export default WithAuth(ThreadPoolsPage)