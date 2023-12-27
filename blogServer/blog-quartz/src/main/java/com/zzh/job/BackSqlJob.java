package com.zzh.job;


import com.zzh.common.annotation.Job;
import com.zzh.common.config.BackProperty;
import com.zzh.job.base.BaseJob;
import com.zzh.utils.BackSqlFile;
import com.zzh.vo.BackSqlVO;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 37436
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Job(name = "BackSqlFile")
public class BackSqlJob implements BaseJob {

    @Autowired
    private BackSqlFile backSqlFile;

    @Autowired
    private BackProperty property;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        property.getDatabases().forEach(item->{
            backSqlFile.backupSqlFile(BackSqlVO.builder()
                    .host(property.getHost())
                    .hostUsername(property.getHostUsername())
                    .hostPwd(property.getHostPwd())
                    .databases(item)
                    .dataUsername(property.getDatabaseUsername())
                    .dataPwd(property.getDatabasePwd())
                    .backupDir(property.getBackupDir()).build());
        });
    }
}
