package ignore;

import com.william.boss.orm.model.BossUserRole;
import com.william.boss.orm.dao.BossUserRoleMapper;
import ignore.IBossUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统级别用户角色关联表 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossUserRoleServiceImpl extends ServiceImpl<BossUserRoleMapper, BossUserRole> implements IBossUserRoleService {

}
