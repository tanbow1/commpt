//package com.tb.commpt.interceptor;
//
//import org.apache.cxf.interceptor.Fault;
//import org.apache.cxf.message.Message;
//import org.apache.cxf.phase.AbstractPhaseInterceptor;
//import org.apache.cxf.phase.Phase;
//
///**
// * Created by Tanbo on 2017/8/29.
// */
//public class CxfHandleInterceptor extends AbstractPhaseInterceptor<Message> {
//
//    public CxfHandleInterceptor(String phase) {
//        super(phase);
//    }
//
//    public CxfHandleInterceptor() {
//        super(Phase.RECEIVE);
//    }
//
//    @Override
//    public void handleMessage(Message message) throws Fault {
////拦截了一个消息，并做处理
//    }
//
//    @Override
//    public void handleFault(Message message) {
//        super.handleFault(message);
//        System.out.println("拦截消息处理失败");
//    }
//}
