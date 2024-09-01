import {LockOutlined, UserOutlined,} from '@ant-design/icons';
import {LoginForm, ProConfigProvider, ProFormText,} from '@ant-design/pro-components';
import {notification, Tabs} from 'antd';
import {CSSProperties, useState} from 'react';
import {login} from "@/api/auth";
import {setValueToStore, STORE_KEY} from "@/helper/store";
import {useNavigate} from "@@/exports";

type LoginType = 'account';

export default () => {
    const [loginType, setLoginType] = useState<LoginType>('account');
    const [notificationApi, contextHolder] = notification.useNotification();
    const navigate = useNavigate();

    const formStyle: CSSProperties = {
        display: 'flex',
        flexDirection: 'column',
        height: '100vh',
        overflow: 'auto',
        backgroundImage:
            "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
        backgroundSize: '100% 100%',
    }

    const onLoginSubmit = async (loginForm) => {
        login(loginForm)
            .then(res => {
                const unwrap = res.data
                if (unwrap.status !== 200) {
                    notificationApi.error({
                        message: unwrap.message,
                        description: unwrap.description,
                        showProgress: true,
                        pauseOnHover: true
                    })
                    return
                }

                // 登录成功
                const token = unwrap.data.authToken
                setValueToStore(STORE_KEY.AUTH_TOKEN, token)
                notificationApi.success({
                    message: '登录成功',
                    description: unwrap.message,
                    showProgress: false,
                    pauseOnHover: true,
                    duration: 0.5,
                    onClose() {
                        navigate("/")
                    }
                })
            })
            .catch(err => {
                notificationApi.error({
                    message: '系统错误',
                    description: err.message,
                    showProgress: true,
                    pauseOnHover: true
                })
            })
    }

    return (
        <ProConfigProvider hashed={false}>
            {contextHolder}
            <div style={formStyle}>
                <LoginForm
                    autoComplete={"off"}
                    contentStyle={{
                        minWidth: 280,
                        maxWidth: '75vw',
                    }}
                    title="分布式动态线程池组件"
                    subTitle="动态管理线程池配置参数&监控线程池运行状况"
                    onFinish={onLoginSubmit}
                >
                    <Tabs
                        centered
                        activeKey={loginType}
                        onChange={(activeKey) => setLoginType(activeKey as LoginType)}
                    >
                        <Tabs.TabPane key={'account'} tab={'账号密码登录'}/>
                    </Tabs>
                    {loginType === 'account' && (
                        <>
                            <ProFormText

                                name="username"
                                fieldProps={{
                                    size: 'large',
                                    prefix: <UserOutlined className={'prefixIcon'}/>,
                                }}
                                placeholder={'请输入用户名 (默认: dtp)'}
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入用户名!',
                                    },
                                ]}
                            />
                            <ProFormText.Password
                                name="password"
                                fieldProps={{
                                    size: 'large',
                                    prefix: <LockOutlined className={'prefixIcon'}/>
                                }}
                                placeholder={'请输入密码 (默认: dtp)'}
                                rules={[
                                    {
                                        required: true,
                                        message: '请输入密码！',
                                    },
                                ]}
                            />
                        </>
                    )}
                </LoginForm>
            </div>
        </ProConfigProvider>
    );
};