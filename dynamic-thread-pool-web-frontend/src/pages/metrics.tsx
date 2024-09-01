import {useEffect, useRef} from "react";
import WithAuth from "@/componets/WithAuth";

const MetricsPage = () => {
    return (
       <iframe
           id="grafana"
           width="100%"
           src={window['grafanaDashboardUrl']}
           allowFullScreen
           style={{height: '150vh', borderStyle: 'solid', borderWidth: '1px', borderColor: '#efefef'}}
       ></iframe>
    );
};

export default WithAuth(MetricsPage);
