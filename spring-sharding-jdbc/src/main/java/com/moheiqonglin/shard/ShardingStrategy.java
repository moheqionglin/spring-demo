package com.moheiqonglin.shard;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Range;
import org.joda.time.DateTime;

import java.util.*;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 2:18 PM
 */
public class ShardingStrategy implements SingleKeyTableShardingAlgorithm<Long> {
    private final long ONE_DAY = 24 * 60 * 60 * 1000l;

    /**
     * allActualTableNames 所有的物理表名；shardingValue 分表的key值属性
     */
    public String doEqualSharding(final Collection<String> allActualTableNames,
                                  final ShardingValue<Long> shardingValue) {
        // 逻辑表名
        String logicTableName = shardingValue.getLogicTableName();
        // 根据比较的值，算出物理分表
        String actualTableName = getTableByDate(logicTableName + "_", new Date(shardingValue.getValue()));
        if (allActualTableNames.contains(actualTableName))
            return actualTableName;

        // 如果没有匹配到相应的物理表名，那一定是有问题的
        throw new UnsupportedOperationException();
    }
    public String getTableByDate(String prefix, Date date){
        DateTime dateTime = new DateTime(date);
        int year =  dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        return prefix + year + "_" + month ;
    }


    /**
     * 支持分表字段的in表达式
     */
    @Override
    public Collection<String> doInSharding(Collection<String> allActualTableNames,
                                           ShardingValue<Long> paramShardingValue) {
        // in表达式的值对应的数据表
        Set<String> inValueTables = new HashSet<String>();
        Collection<Long> inValues = paramShardingValue.getValues();

        String logicTableName = paramShardingValue.getLogicTableName();
        for (Long value : inValues) {
            String actualTableName = getTableByDate(logicTableName + "_", new Date(value));
            if (allActualTableNames.contains(actualTableName))
                inValueTables.add(actualTableName);
        }

        if (inValueTables.size() == 0)
            throw new UnsupportedOperationException();

        return inValueTables;
    }

    @Override
    public Collection<String> doBetweenSharding(Collection<String> allActualTableNames,
                                                ShardingValue<Long> paramShardingValue) {
        // in表达式的值对应的数据表
        Set<String> inValueTables = new HashSet<String>();
        Range<Long> valueRange = paramShardingValue.getValueRange();
        String logicTableName = paramShardingValue.getLogicTableName();

        List<String> tables = getTables(logicTableName + "_", valueRange.lowerEndpoint(), valueRange.upperEndpoint());

        for (String table : tables) {
            if (allActualTableNames.contains(table))
                inValueTables.add(table);
        }

        if (inValueTables.size() == 0)
            throw new UnsupportedOperationException();

        return inValueTables;
    }
    public static long getDayEnd(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);
        return calendar.getTimeInMillis();
    }
    public static long getDayStart(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    public List<String> getTables(String tablePrefix, long beginTimeMs, long endTimeMs){
        List<String> queryTables = new ArrayList<>();
        long endTime = getDayEnd(endTimeMs);
        for(long time = getDayStart(beginTimeMs); time <= endTime; time += ONE_DAY){
            String table = getTableByDate(tablePrefix, new Date(time));
            if(queryTables.contains(table)){
                continue;
            }
            queryTables.add(table);
        }
        return queryTables;
    }
}