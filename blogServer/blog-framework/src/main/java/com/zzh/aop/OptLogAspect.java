package com.zzh.aop;

import com.alibaba.fastjson.JSON;
import com.zzh.common.utils.IpUtils;
import com.zzh.utils.SecurityUtils;
import com.zzh.entity.LoginUser;
import com.zzh.entity.OperationLog;
import com.zzh.entity.User;
import com.zzh.mapper.OperationLogMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * @Author zzh
 * @Date 2021/3/8 21:00
 * @Version 0.1
 * @Description 操作日志切面处理
 **/

@Aspect
@Component
public class OptLogAspect {
    @Autowired
    private OperationLogMapper operationLogMapper;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     * @annotation 匹配包含该注解的方法
     */
    @Pointcut("@annotation(com.zzh.aop.OptLog)")
    public void optLogPointCut() {
    }


    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     * AfterReturning 使用后置通知(方法返回后),引入optLogPointCut切入点,表示此通知对注解OptLog生效
     * @param joinPoint 切入点
     * @param keys      返回结果
     */
    @Transactional(rollbackFor = Exception.class)
    @AfterReturning(value = "optLogPointCut()", returning = "keys")
    public void saveOptLog(JoinPoint joinPoint, Object keys) {
        // 获取ServletRequestAttributes
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 从获取ServletRequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
        OperationLog operationLog = new OperationLog();
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();
        // 反射
        // 获取操作模块，也就是提取每个类上的 @Api 注解中的信息 (Swagger注解)
        Api api = (Api) signature.getDeclaringType().getAnnotation(Api.class);
        // 提取方法上的 @ApiOperation 注解中的信息 (Swagger注解)
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        OptLog optLog = method.getAnnotation(OptLog.class);
        // 操作模块(Api注解中的参数)
        operationLog.setOptModule(api.tags()[0]);
        // 操作类型
        operationLog.setOptType(optLog.optType());
        // 操作描述
        operationLog.setOptDesc(apiOperation.value());
        // 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 请求方式
        operationLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        // 请求方法
        operationLog.setOptMethod(methodName);
        // 请求参数
        operationLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
        // 返回结果
        operationLog.setResponseData(JSON.toJSONString(keys));
        // 得到登录用户
        User user = ((LoginUser) SecurityUtils.getAuthentication().getPrincipal()).getUser();
        // 请求用户ID
        operationLog.setUserId(user.getId());
        // 请求用户
        operationLog.setUsername(user.getUsername());
        // 请求IP
        String ipAddr = IpUtils.getIpAddr(request);
        operationLog.setIpAddr(ipAddr);
        operationLog.setIpSource(IpUtils.getIpSource(ipAddr));
        // 请求URL
        operationLog.setOptUrl(request.getRequestURI());
        // 创建时间
        operationLog.setCreateTime(new Date());
        save(operationLog);
    }

    @Async
    public void save(OperationLog operationLog){
        operationLogMapper.insert(operationLog);
    }
}
