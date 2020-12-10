package ignore;

import com.william.boss.orm.model.BossDataAuth;
import com.william.boss.orm.dao.BossDataAuthMapper;
import ignore.IBossDataAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据权限表 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossDataAuthServiceImpl extends ServiceImpl<BossDataAuthMapper, BossDataAuth> implements IBossDataAuthService {

}
