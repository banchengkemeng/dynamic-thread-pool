import React, {useEffect} from "react";
import {ModalForm, ProFormText,} from '@ant-design/pro-components';
import {Form, message} from 'antd';
import {ProFormDigit} from "@ant-design/pro-form/lib";

const waitTime = (time: number = 100) => {
    return new Promise((resolve) => {
        setTimeout(() => {
            resolve(true);
        }, time);
    });
};

export type UpdatePoolConfigType = {
    applicationName: string;
    threadPoolName: string;
    corePoolSize: number;
    maximumPoolSize: number;
    queueCapacity: number;
}

const UpdatePoolConfigForm: React.FC<{
    key: number;
    initialValue: UpdatePoolConfigType;
    onConfirm: (formValue) => void
}> = ({initialValue, onConfirm}) => {
    const [form] = Form.useForm<UpdatePoolConfigType>();

    useEffect(() => {
        form.setFieldsValue(initialValue)
    }, [initialValue]);

    return (
        <ModalForm<UpdatePoolConfigType>
            width="60vh"
            title="修改线程池参数"
            trigger={
                <a type="link">修改参数</a>
            }
            form={form}
            submitTimeout={2000}
            onFinish={onConfirm}
            on
        >
            <ProFormText disabled width="md" name="applicationName" label="应用" />
            <ProFormText disabled width="md" name="threadPoolName" label="线程池" />
            <ProFormDigit width="md" name="corePoolSize" label="核心线程数" />
            <ProFormDigit width="md" name="maximumPoolSize" label="最大线程数" />
            <ProFormDigit width="md" name="queueCapacity" label="阻塞队列大小" />
        </ModalForm>
    );
}

export default UpdatePoolConfigForm