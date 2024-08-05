package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto;

import cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.entity.ThreadPoolConfigEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateThreadPoolConfigDTO {
    private String applicationName;
    private String threadPoolName;
    private Integer corePoolSize;
    private Integer maximumPoolSize;

    public static UpdateThreadPoolConfigDTO buildUpdateThreadPoolConfigDTO(
            ThreadPoolConfigEntity threadPoolConfigEntity ) {
        return new UpdateThreadPoolConfigDTO(
                threadPoolConfigEntity.getApplicationName(),
                threadPoolConfigEntity.getThreadPoolName(),
                threadPoolConfigEntity.getCorePoolSize(),
                threadPoolConfigEntity.getMaximumPoolSize()
        );
    }
}
