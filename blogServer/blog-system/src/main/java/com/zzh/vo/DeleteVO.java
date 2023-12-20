package com.zzh.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * @Author zzh
 * @Date 2021/7/28 10:15
 * @Version 0.1
 * @Description 文章逻辑删除接收
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "逻辑删除")
public class DeleteVO {
    /**
     * id列表
     * 批量删除和单个删除
     */
    @ApiModelProperty(name = "idList", value = "id列表", required = true, dataType = "List<Integer>")
    private List<Long> idList;

    /**
     * 状态值
     */
    @ApiModelProperty(name = "isDelete", value = "删除状态", required = true, dataType = "Integer")
    @Range(max = 1,min = 0,message = "删除状态为0或1")
    private Integer isDelete;
}
