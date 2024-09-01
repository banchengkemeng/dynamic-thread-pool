import {ProLayout} from '@ant-design/pro-components';
import {Link, Outlet, useAppData, useLocation} from 'umi';

export default function Layout() {
    const { clientRoutes } = useAppData();
    const location = useLocation();

    return (
        <ProLayout
            route={clientRoutes[clientRoutes.length > 1 ? 1 : 0]}
            location={location}
            layout="top"
            logo={null}
            title="分布式动态线程池"
            menuItemRender={(menuItemProps, defaultDom) => {
                if (menuItemProps.isUrl || menuItemProps.children) {
                    return defaultDom;
                }

                if (menuItemProps.path && location.pathname !== menuItemProps.path) {
                    return (
                        <Link to={menuItemProps.path} target={menuItemProps.target}>
                            {defaultDom}
                        </Link>
                    );
                }
                return defaultDom;
            }}
        >
            <Outlet />
        </ProLayout>
    );
}