package com.hit.cache.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.redisson.config.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties("app.external-cache")
public class ExternalCacheConfigProperties {

    private Boolean enable;
    private String applicationCache = "DEFAULT";
    private String delimiter = "::";
    private Long cacheDefaultExpire = 864000L; // seconds
    private Map<String, Long> cacheExpirations = new HashMap<>();
    private RedisCacheProperties redisson;

    @Setter
    @Getter
    public static class RedisCacheProperties extends Config {

        @Override
        public SentinelServersConfig getSentinelServersConfig() {
            return super.getSentinelServersConfig();
        }

        @Override
        public void setSentinelServersConfig(SentinelServersConfig sentinelConnectionConfig) {
            super.setSentinelServersConfig(sentinelConnectionConfig);
        }

        @Override
        public MasterSlaveServersConfig getMasterSlaveServersConfig() {
            return super.getMasterSlaveServersConfig();
        }

        @Override
        public void setMasterSlaveServersConfig(MasterSlaveServersConfig masterSlaveConnectionConfig) {
            super.setMasterSlaveServersConfig(masterSlaveConnectionConfig);
        }

        @Override
        public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
            super.setSingleServerConfig(singleServerConfig);
        }

        @Override
        public SingleServerConfig getSingleServerConfig() {
            return super.getSingleServerConfig();
        }

        @Override
        public ClusterServersConfig getClusterServersConfig() {
            return super.getClusterServersConfig();
        }

        @Override
        public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
            super.setClusterServersConfig(clusterServersConfig);
        }

        @Override
        public ReplicatedServersConfig getReplicatedServersConfig() {
            return super.getReplicatedServersConfig();
        }

        @Override
        public void setReplicatedServersConfig(ReplicatedServersConfig replicatedServersConfig) {
            super.setReplicatedServersConfig(replicatedServersConfig);
        }
    }
}