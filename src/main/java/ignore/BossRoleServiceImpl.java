package ignore;

import com.william.boss.orm.model.BossRole;
import com.william.boss.orm.dao.BossRoleMapper;
import ignore.IBossRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossRoleServiceImpl extends ServiceImpl<BossRoleMapper, BossRole> implements IBossRoleService {

}
