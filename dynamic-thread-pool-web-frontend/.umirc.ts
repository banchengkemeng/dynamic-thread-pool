import {defineConfig} from "umi";

export default defineConfig({
    routes: [
        {path: "/", redirect: "/pools"},
        {path: "/login", layout: false, component: "login"},
        {path: "/pools", component: "pools", name: "线程池"},
        {path: "/metrics", component: "metrics", name: "可视化监控"},
        {path: "/*", redirect: "/"},

    ],
    npmClient: 'npm',
    publicPath: '/f8e5c81e18c8522/',
    hash: true,
    history: {
        type: 'hash'
    },
    outputPath: './dist/web'
});
