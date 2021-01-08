package com.ssd.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author lus
 * @since 2020-12-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ssd_monitor_plate")
@ApiModel(value = "monitorPlate",description = "板块对象monitorPlate")
public class MonitorPlate extends Model<MonitorPlate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(name = "id", value = "主键：新增时为空", required = false, dataType = "Long", example="1")
    private Long id;

    @ApiModelProperty(value = "板块名称",name = "palteName",required = true,dataType = "String")
    private String palteName;

    @ApiModelProperty(value = "板块编码",name = "palteCode",required = true,dataType = "String")
    private String palteCode;

    @ApiModelProperty(value = "地区编码",name = "areaCode",required = true,dataType = "String")
    private String areaCode;

    @ApiModelProperty(value = "板块地址",name = "paltePath",required = true,dataType = "String")
    private String paltePath;

    @ApiModelProperty(value = "是否启用",name = "enable",required = true,dataType = "Integer", example="1")
    private Integer enable;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPalteName() {
        return palteName;
    }

    public void setPalteName(String palteName) {
        this.palteName = palteName;
    }

    public String getPalteCode() {
        return palteCode;
    }

    public void setPalteCode(String palteCode) {
        this.palteCode = palteCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPaltePath() {
        return paltePath;
    }

    public void setPaltePath(String paltePath) {
        this.paltePath = paltePath;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
