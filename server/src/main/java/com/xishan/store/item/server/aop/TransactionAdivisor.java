package com.xishan.store.item.server.aop;

import com.xishan.store.item.server.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * 重写，提交和退回事务的逻辑
 */
public class TransactionAdivisor extends TransactionInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    ExecutorService pool = Executors.newFixedThreadPool(5);

    @Override
    protected void commitTransactionAfterReturning(TransactionInfo txInfo) {
        commitAfter(txInfo);
    }

    @Override
    protected void completeTransactionAfterThrowing(TransactionInfo txInfo, Throwable ex) {

    }

    @Override
    protected void cleanupTransactionInfo(TransactionInfo txInfo) {

    }


    //尝试过一会提交是否可以成功
    public void commitAfter( TransactionInfo txInfo){
        pool.execute(()->{
            try {
                sleep(15000);

            if (txInfo != null && txInfo.getTransactionStatus() != null) {
                if (logger.isTraceEnabled()) {
                    logger.trace("Completing transaction for [" + txInfo.getJoinpointIdentification() + "]");
                }
                txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
            }
        });

    }
    //检测
}

