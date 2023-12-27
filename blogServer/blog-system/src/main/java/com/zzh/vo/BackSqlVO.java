package com.zzh.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author 37436
 */
@Data
@Builder
public class BackSqlVO {
    private String host;
    private String hostUsername;
    private String hostPwd;
    private String dataUsername;
    private String dataPwd;
    private String databases;
    private String backupDir;

}
