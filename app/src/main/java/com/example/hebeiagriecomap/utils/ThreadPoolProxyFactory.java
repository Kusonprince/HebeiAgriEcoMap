package com.example.hebeiagriecomap.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kuson on 17/4/16.
 * 线程池基础封装，需其它方法自行添加
 */

public class ThreadPoolProxyFactory {

    private static ThreadPoolProxy mNormalThreadPoolProxy;

    private static ThreadPoolProxy mLongThreadPoolProxy;

    /**
     * 返回普通线程池的代理
     * 双重检查加锁,保证只有第一次实例化的时候才启用同步机制,提高效率
     * @return
     */
    public static ThreadPoolProxy getNormalThreadPoolProxy() {
        if (mNormalThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mNormalThreadPoolProxy == null) {
                    mNormalThreadPoolProxy = new ThreadPoolProxy(6, 6, 20000);
                }
            }
        }
        return mNormalThreadPoolProxy;
    }

    /**
     * 返回下载线程池的代理
     */
    public static ThreadPoolProxy getLongThreadPoolProxy() {
        if (mLongThreadPoolProxy == null) {
            synchronized (ThreadPoolProxyFactory.class) {
                if (mLongThreadPoolProxy == null) {
                    mLongThreadPoolProxy = new ThreadPoolProxy(3, 3, 30000);
                }
            }
        }
        return mLongThreadPoolProxy;
    }

    public static class ThreadPoolProxy {
        int mCorePoolSize;
        int  mMaximumPoolSize;
        long mKeepAliveTime;

        ThreadPoolExecutor mThreadPoolExecutor;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            mCorePoolSize = corePoolSize;
            mMaximumPoolSize = maximumPoolSize;
            mKeepAliveTime = keepAliveTime;
        }

        private void initThreadPoolExecutor() {
            if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown() || mThreadPoolExecutor.isTerminated()) {
                synchronized (ThreadPoolProxyFactory.class) {
                    if (mThreadPoolExecutor == null || mThreadPoolExecutor.isShutdown() || mThreadPoolExecutor
                            .isTerminated()) {
                        TimeUnit unit = TimeUnit.MILLISECONDS;
                        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue();
                        ThreadFactory threadFactory = Executors.defaultThreadFactory();
                        RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
                        mThreadPoolExecutor = new ThreadPoolExecutor(
                                mCorePoolSize, //核心池的大小
                                mMaximumPoolSize, //最大线程数
                                mKeepAliveTime,//保持时间
                                unit,//保持时间的单位
                                workQueue,//工作队列
                                threadFactory,//线程工厂
                                handler//异常捕获器
                        );
                    }
                }
            }
        }

        /**
         * 提交任务
         */
        public Future<?> submit(Runnable task) {
            initThreadPoolExecutor();
            Future<?> submitResult = mThreadPoolExecutor.submit(task);
            return submitResult;
        }

        /**
         * 执行任务
         */
        public void execute(Runnable task) {
            initThreadPoolExecutor();
            mThreadPoolExecutor.execute(task);
        }

        /**
         * 移除任务
         */
        public void remove(Runnable task) {
            initThreadPoolExecutor();
            mThreadPoolExecutor.remove(task);
        }
    }

}
