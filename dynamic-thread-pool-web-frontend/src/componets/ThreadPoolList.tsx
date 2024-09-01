import type {ProColumns} from '@ant-design/pro-components';
import {ProTable} from '@ant-design/pro-components';
import {Button, message} from 'antd';
import React from "react";
import UpdatePoolConfigForm from "@/componets/UpdatePoolConfigForm";
import {ThreadPoolConfigType, updateThreadPoolConfig} from "@/api/pool";

export type ThreadPoolListItem = {key: number} & ThreadPoolConfigType

const ThreadPoolList: React.FC<{
    threadPoolList: ThreadPoolListItem[],
    loading: boolean,
    onRefresh: () => void,
    onUpdateThreadPoolConfig: (form: ThreadPoolConfigType) => Promise<boolean>
}> = ({loading, threadPoolList, onRefresh, onUpdateThreadPoolConfig}) => {


    const columns: ProColumns<ThreadPoolListItem>[] = [
        {
            title: '应用',
            width: 80,
            dataIndex: 'applicationName',
            render: (_) => <a>{_}</a>,
        },
        {
            title: '线程池',
            width: 80,
            dataIndex: 'threadPoolName',
            // render: (_) => <a>{_}</a>,
        },
        {
            title: '核心线程数',
            width: 50,
            dataIndex: 'corePoolSize',
            // render: (_) => <a>{_}</a>,
        },
        {
            title: '最大线程数',
            width: 50,
            dataIndex: 'maximumPoolSize',
            // render: (_) => <a>{_}</a>,
        },
        {
            title: '活跃线程数',
            width: 50,
            dataIndex: 'activeThreadCount',
            // render: (_) => <a>{_}</a>,
        },
        {
            title: '工作线程数',
            width: 50,
            dataIndex: 'poolSize',
            // render: (_) => <a>{_}</a>,
        },
        {
            title: '阻塞队列类型',
            width: 80,
            dataIndex: 'queueType',
            // render: (_) => <a>{_}</a>,
        },
        {
            title: '队列任务数',
            width: 50,
            dataIndex: 'queueSize',
            // render: (_) => <a>{_}</a>,
        },
        {
            title: '队列剩余容量',
            width: 50,
            dataIndex: 'remainingCapacity',
            // render: (_) => <a>{_}</a>,
        },
        {
            title: '操作',
            width: 80,
            key: 'option',
            valueType: 'option',
            render: (_, row) => [
                <UpdatePoolConfigForm
                    key={row.key}
                    initialValue={{
                        applicationName: row.applicationName,
                        threadPoolName: row.threadPoolName,
                        corePoolSize: row.corePoolSize,
                        maximumPoolSize: row.maximumPoolSize,
                        queueCapacity: row.queueSize + row.remainingCapacity
                    }}
                    onConfirm={onUpdateThreadPoolConfig}
                />
            ],
        },
    ];

    return (
        <>
            <ProTable<ThreadPoolListItem>
                dataSource={threadPoolList}
                rowKey="key"
                pagination={{
                    showQuickJumper: true,
                }}
                loading={loading}
                columns={columns}
                search={false}
                dateFormatter="string"
                headerTitle="线程池列表"
                toolBarRender={() => [
                    <Button type="primary" onClick={onRefresh}>拉取最新数据</Button>
                ]}
            />
        </>
    );
};

export default ThreadPoolList