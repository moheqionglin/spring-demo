package com.moheiqonglin.shard.multipleDatasource.domain;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 11:40 AM
 */
public class SplitDbConfigBean {
    private int id;
    private String url;
    private String shardKey;
    private String userName;
    private String password;

    public SplitDbConfigBean() {
    }

    public SplitDbConfigBean(int id, String url, String shardKey, String userName, String password) {
        this.id = id;
        this.url = url;
        this.shardKey = shardKey;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShardKey() {
        return shardKey;
    }

    public void setShardKey(String shardKey) {
        this.shardKey = shardKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SplitDbConfigBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", shardKey='" + shardKey + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}