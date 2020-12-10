package ignore;

import com.william.boss.orm.model.BossOperationLog;
import com.william.boss.orm.dao.BossOperationLogMapper;
import ignore.IBossOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统操作日志表 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossOperationLogServiceImpl extends ServiceImpl<BossOperationLogMapper, BossOperationLog> implements IBossOperationLogService {

}
