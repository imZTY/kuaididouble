package com.zty.kdd.limiter;

import java.time.Duration;
import java.util.function.Supplier;

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.zty.kdd.util.PropertyUtils;

/**
 * @author: Tianyi.Zeng
 * @create: 7/4/2021 - 上午 10:35
 */
@Component
public class HttpRateLimitPool {

    private Logger logger = LogManager.getLogger(this.getClass());

    private final ThreadPoolBulkhead netmchPushThreadPool;
    private final RateLimiter netmchPushLimiter;

    // 线程池的配置（可在 threadpool.properties 配置）
    int coreThread = 8; // 核心线程数
    int maxThread = 10; // 最大线程数
    int queueCapacity = 100; // 任务队列容量

    // 限频的配置（可在 threadpool.properties 配置）
    int secondsOfPeriod = 1; // 限频周期秒数
    int limitForPeriod = 2; // 周期请求次数限制
    int secondsOfQueueTimeout = 300; // 任务等待超时秒数

    public HttpRateLimitPool() {
        // 读取配置文件修改配置
        initConfigParams();

        // 初始化限频器
        RateLimiterConfig netmchPushConfig = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(secondsOfPeriod))
                .limitForPeriod(limitForPeriod)
                .timeoutDuration(Duration.ofSeconds(secondsOfQueueTimeout))
                .build();
        netmchPushLimiter = RateLimiter.of("netmchPushLimiter", netmchPushConfig);

        // 初始化线程池
        ThreadPoolBulkheadConfig netmchPushThreadPoolConfig = ThreadPoolBulkheadConfig.custom()
                .maxThreadPoolSize(maxThread)
                .coreThreadPoolSize(coreThread)
                .queueCapacity(queueCapacity).build();
        netmchPushThreadPool = ThreadPoolBulkhead
                .of("netmchPushThreadPool", netmchPushThreadPoolConfig);
    }

    /**
     * 读取 threadpool.properties 初始化线程池配置
     */
    private void initConfigParams() {
        try {
            // 线程池相关
            String coreThreadStr = PropertyUtils.getValue("threadpool.properties", "httpThreadPool.coreThread");
            if (StringUtils.isNotBlank(coreThreadStr)) {
                logger.info("读取到核心线程数配置:{}", coreThreadStr);
                coreThread = Integer.parseInt(coreThreadStr);
            }
            String maxThreadStr = PropertyUtils.getValue("threadpool.properties", "httpThreadPool.maxThread");
            if (StringUtils.isNotBlank(maxThreadStr)) {
                logger.info("读取到最大线程数配置:{}", maxThreadStr);
                maxThread = Integer.parseInt(maxThreadStr);
            }
            String queueCapacityStr = PropertyUtils.getValue("threadpool.properties", "httpThreadPool.queueCapacity");
            if (StringUtils.isNotBlank(queueCapacityStr)) {
                logger.info("读取到任务队列最大容量配置:{}", queueCapacityStr);
                queueCapacity = Integer.parseInt(queueCapacityStr);
            }
            // 限频器相关
            String secondsOfPeriodStr = PropertyUtils.getValue("threadpool.properties", "httpThreadPool.secondsOfPeriod");
            if (StringUtils.isNotBlank(secondsOfPeriodStr)) {
                logger.info("读取到周期秒数配置:{}", secondsOfPeriodStr);
                secondsOfPeriod = Integer.parseInt(secondsOfPeriodStr);
            }
            String limitForPeriodStr = PropertyUtils.getValue("threadpool.properties", "httpThreadPool.limitForPeriod");
            if (StringUtils.isNotBlank(limitForPeriodStr)) {
                logger.info("读取到周期执行频数限制配置:{}", limitForPeriodStr);
                limitForPeriod = Integer.parseInt(limitForPeriodStr);
            }
            String secondsOfQueueTimeoutStr = PropertyUtils.getValue("threadpool.properties", "httpThreadPool.secondsOfQueueTimeout");
            if (StringUtils.isNotBlank(secondsOfQueueTimeoutStr)) {
                logger.info("读取到任务等待超时秒数配置:{}", secondsOfQueueTimeoutStr);
                secondsOfQueueTimeout = Integer.parseInt(secondsOfQueueTimeoutStr);
            }
        } catch (Exception e) {
            logger.error("读取http限频线程池配置异常", e);
        }
    }

    /**
     * 处理Supplier并返回结果
     * @param supplier 业务逻辑代码
     * @return String结果
     */
    public void dealSupplier(Supplier<Integer> supplier) {
        // 注意，这里的顺序一定要是 withRateLimiter 在前面，否则不会有限频效果
        Decorators.ofSupplier(supplier)
            .withRateLimiter(netmchPushLimiter)
            .withThreadPoolBulkhead(netmchPushThreadPool)
            .get().toCompletableFuture()
            .whenComplete((result, throwable) -> {
                if (throwable != null) {
                    logger.error("处理失败, 异常信息: ", throwable);
                }
            }).join();
    }
}
