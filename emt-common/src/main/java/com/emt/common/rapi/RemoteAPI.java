package com.emt.common.rapi;

import com.emt.base.entity.RemoteApiParams;
import com.emt.common.httpclient.HttpClientEx;
import com.emt.common.httpclient.HttpsClientUtil;
import com.emt.common.utils.Encodes;
import com.emt.common.security.BaseCoder;

import java.util.HashMap;
import java.util.Map;

public class RemoteAPI {

    public static String  CallRemoteAPI(RemoteApiParams params) throws Exception  {
        String base64Str = Encodes.encodeBase64(params.getJsonParams().getBytes());
        String md5Str = BaseCoder.MD5(base64Str + params.getKey());

        Map<String,String> map = new HashMap<String,String>();
        map.put("interfaceName",params.getInterfaceName());
        map.put("interfaceVersion",params.getInterfaceVersion());
        map.put("qid",params.getQid().toString());
        map.put("tranData",base64Str);
        map.put("signData",md5Str);
        map.put("clientType",params.getClientType().toString());
        map.put("busiId", params.getBusiId().toString());
        String _retusn = "";

        System.out.println("调用URL="+params.getUrl());
        if (params.getUrl().substring(0, 8).equals("https://")) {
            _retusn = HttpsClientUtil.doPost(params.getUrl(), "utf-8", map);
        }else
        {
            _retusn = HttpClientEx.remotePost(params.getUrl(), map);
        }
        return  _retusn;
    }

}
