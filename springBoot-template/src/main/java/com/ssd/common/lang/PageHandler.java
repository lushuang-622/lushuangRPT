package com.ssd.common.lang;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssd.common.utils.LocalLogUtils;
import com.ssd.monitor.entity.MonitorPlate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分页查询欲处理
 *
 * @author lus
 * @Date 2021/1/5 0005 08
 */
public class PageHandler {
    private static final List<String> pageList = Arrays.asList("limit", "size", "sortName", "sortOrder");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    /**
     * 页码 默认0
     */
    private Integer limit;
    /**
     * 每页条数 默认10
     */
    private Integer size;
    /**
     * orderBy 字段 默认id
     */
    private String sortName;
    /**
     * 排序类型 asc desc 默认 desc
     */
    private String sortOrder;
    /**
     * 完全匹配条件集合
     */
    private Map<String, Object> queryMap;

    /**
     * like 查询条件集合
     */
    private Map<String, String> likeMap;

    private Long ltNumber;
    private Long lteqNumber;
    private Long gtNumber;
    private Long gteqNumber;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date updateTime;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 获取完全匹配查询条件
     * 例： QueryWrapper<MonitorPlate> query = new QueryWrapper<>();
     *         query.allEq(pageHandler.getQueryMap());
     * @return
     */
    public Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, Object> queryMap) {
        this.queryMap = queryMap;
    }

    /**
     * 获取模糊匹配查询条件
     * 例：QueryWrapper<MonitorPlate> query = new QueryWrapper<>();
     *         if(pageHandler.getLikeMap() != null){
     *             pageHandler.getLikeMap().forEach((key, value) -> {
     *                 query.and(wrapper -> wrapper.like(key,value));
     *             });
     *         }
     * @return
     */
    public Map<String, String> getLikeMap() {
        return likeMap;
    }

    public void setLikeMap(Map<String, String> likeMap) {
        this.likeMap = likeMap;
    }

    public Long getLtNumber() {
        return ltNumber;
    }

    public void setLtNumber(Long ltNumber) {
        this.ltNumber = ltNumber;
    }

    public Long getLteqNumber() {
        return lteqNumber;
    }

    public void setLteqNumber(Long lteqNumber) {
        this.lteqNumber = lteqNumber;
    }

    public Long getGtNumber() {
        return gtNumber;
    }

    public void setGtNumber(Long gtNumber) {
        this.gtNumber = gtNumber;
    }

    public Long getGteqNumber() {
        return gteqNumber;
    }

    public void setGteqNumber(Long gteqNumber) {
        this.gteqNumber = gteqNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public PageHandler(Integer limit, Integer size, String sortName, String sortOrder, Map<String, Object> queryMap, Map<String, String> likeMap) {
        this.limit = limit;
        this.size = size;
        this.sortName = sortName;
        this.sortOrder = sortOrder;
        this.queryMap = queryMap;
        this.likeMap = likeMap;
    }

    public PageHandler(Integer limit, Integer size, String sortName, String sortOrder, Map<String, Object> queryMap) {
        this.limit = limit;
        this.size = size;
        this.sortName = sortName;
        this.sortOrder = sortOrder;
        this.queryMap = queryMap;
    }

    public PageHandler(Integer limit, Integer size, String sortName, String sortOrder) {
        this.limit = limit;
        this.size = size;
        this.sortName = sortName;
        this.sortOrder = sortOrder;
    }

    /**
     * 通过传递的查询条件按照规则自动匹配生成分页处理类
     *  参数说明：
     *      limit 页面 （0~100...） 默认 0
     *      size 没有条数 默认10
     *      sortName 排序查询的字段 默认 id
     *      sortOrder 排序的顺序类型 （desc/asc） 默认 desc
     *      以“L_"开头的字段封装到 likeMap中
     *      以“lt_"开头的字段表示匹配 "<",类型为Long类型。
     *      以“lteq_"开头的字段表示匹配 "<=",类型为Long类型。
     *      以“gt_"开头的字段表示匹配 ">",类型为Long类型。
     *      以“gteq_"开头的字段表示匹配 "<=",类型为Long类型。
     *      以“st_"开头的字段表示匹配 "start_time",类型为date类型,格式为 “yyyyMMdd-HH:mm:ss”。
     *      以“et_"开头的字段表示匹配 "end_time",类型为date类型,格式为 “yyyyMMdd-HH:mm:ss”。
     *      以“ct_"开头的字段表示匹配 "create_time",类型为date类型,格式为 “yyyyMMdd-HH:mm:ss”。
     *      以“ut_"开头的字段表示匹配 "update_time",类型为date类型,格式为 “yyyyMMdd-HH:mm:ss”。
     *
     * @param map
     */
    public PageHandler(Map<String, Object> map) {
        this.limit = Integer.parseInt(map.get("limit").toString());
        if (this.limit == null) {
            this.limit = 0;
        }
        this.size = Integer.parseInt(map.get("size").toString());
        if (this.size == null) {
            this.size = 10;
        }
        this.sortName = (String) map.get("sortName");
        if (StringUtils.isEmpty(this.sortName)) {
            this.sortName = "id";
        }
        this.sortOrder = (String) map.get("sortOrder");
        if (StringUtils.isEmpty(this.sortOrder)) {
            this.sortOrder = "desc";
        }
        this.sortOrder = sortOrder.toLowerCase();
        map.forEach((key, value) -> {
            if (!pageList.contains(key)) {
                if(key.startsWith("L_")){
                    //like查询条件
                    if(this.likeMap == null){
                        this.likeMap = new HashMap<>();
                    }
                    this.likeMap.put(humpToLine(key.substring(2)), value.toString());
                }else if(key.startsWith("lt_")){
                    this.ltNumber = Long.valueOf(value.toString());
                }else if(key.startsWith("lteq_")){
                    this.lteqNumber= Long.valueOf(value.toString());
                }else if(key.startsWith("gt_")){
                    this.gtNumber = Long.valueOf(value.toString());
                }else if(key.startsWith("gteq_")){
                    this.gteqNumber = Long.valueOf(value.toString());
                }else if(key.startsWith("st_")){
                    try {
                        this.startTime = DateUtils.parseDate(value.toString(),"yyyyMMdd-HH:mm:ss");
                    } catch (ParseException e) {
                        LocalLogUtils.error(value.toString()+"参数转换时间失败！",e);
                    }
                }else if(key.startsWith("et_")){
                    try {
                        this.endTime = DateUtils.parseDate(value.toString(),"yyyyMMdd-HH:mm:ss");
                    } catch (ParseException e) {
                        LocalLogUtils.error(value.toString()+"参数转换时间失败！",e);
                    }
                }else if(key.startsWith("ct_")){
                    try {
                        this.createTime = DateUtils.parseDate(value.toString(),"yyyyMMdd-HH:mm:ss");
                    } catch (ParseException e) {
                        LocalLogUtils.error(value.toString()+"参数转换时间失败！",e);
                    }
                }else if(key.startsWith("ut_")){
                    try {
                        this.updateTime = DateUtils.parseDate(value.toString(),"yyyyMMdd-HH:mm:ss");
                    } catch (ParseException e) {
                        LocalLogUtils.error(value.toString()+"参数转换时间失败！",e);
                    }
                }else{
                    //完全匹配查询条件
                    if(this.queryMap == null){
                        this.queryMap = new HashMap<>();
                    }
                    this.queryMap.put(humpToLine(key), value);
                }
            }
        });
    }


    private PageHandler(Builder builder) {
        this.limit = builder.limit;
        this.size = builder.size;
        this.sortName = builder.sortName;
        this.sortOrder = builder.sortOrder;
        this.queryMap = builder.queryMap;
        this.likeMap = builder.likeMap;
    }

    public static class Builder {
        private Integer limit;
        private Integer size;
        private String sortName;
        private String sortOrder;
        private Map<String, Object> queryMap;
        private Map<String, String> likeMap;

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder sortName(String sortName) {
            this.sortName = sortName;
            return this;
        }

        public Builder sortOrder(String sortOrder) {
            this.sortOrder = sortOrder;
            return this;
        }

        public Builder queryMap(Map<String, Object> queryMap) {
            this.queryMap = queryMap;
            return this;
        }

        public Builder likeMap(Map<String, String> likeMap) {
            this.likeMap = likeMap;
            return this;
        }

        public PageHandler build() {
            return new PageHandler(this);
        }
    }

    /**
     * 驼峰类型转下划线
     * @param str
     * @return
     */
    String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
