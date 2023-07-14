package org.ymall.service;

/**
 * GatewayHandler
 *
 * @Author IIIDelay
 * @Date 2023/7/13 21:53
 **/
public abstract class GatewayHandler {
    private GatewayHandler gatewayHandler;

    public abstract void valid();

    public void next() {
        if (gatewayHandler != null) {
            gatewayHandler.valid();
        }
    }

    /**
     * setNextGatewayHandler : 工程只是帮你建好对象, 不是让你调用, controller才是调用
     *
     * @param handler handler
     */
    public void setNextGatewayHandler(GatewayHandler handler) {
        this.gatewayHandler = handler;
    }
}
