package site.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.model.dto;

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
}
