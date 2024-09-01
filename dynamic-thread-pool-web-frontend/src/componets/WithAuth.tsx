import {useNavigate} from "@@/exports";
import {checkAuth} from "@/api/auth";
import {Component, useEffect} from "react";
import {message} from "antd";

const withAuth = (Component) => () => {
    const [messageApi, contextHolder] = message.useMessage()
    const navigate = useNavigate();
    const openErrorMessageAlert = (content) => {
        messageApi.error(
            content,
            0.7,
            () => {
                navigate('/login')
            }
        )
    }

    useEffect(() => {
        checkAuth()
            .then(res => {
                const unwrap = res.data
                if (unwrap.status !== 200) {
                    openErrorMessageAlert(
                        `${unwrap.message}:${unwrap.description}`
                    )
                    return
                }

                if (!unwrap.data.isLogin) {
                    openErrorMessageAlert('无权限')
                    return;
                }
            })
            .catch(err => {
                openErrorMessageAlert(err.message)
            })
    }, [])

    return (
        <>
            {contextHolder}
            <Component />
        </>
    )
}

export default withAuth