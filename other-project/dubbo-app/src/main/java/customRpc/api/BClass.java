package customRpc.api;

import com.alibaba.dubbo.rpc.RpcContext;

/**
 * @author wanli.zhou
 * @description
 * @time 23/11/2018 5:18 PM
 */
public class BClass {

    public void print() {

        System.out.println("Bclass" + RpcContext.getContext().getAttachment("test"));
    }
}