package ignore;

import com.william.boss.orm.model.BossRoleMenu;
import com.william.boss.orm.dao.BossRoleMenuMapper;
import ignore.IBossRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单关系表 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossRoleMenuServiceImpl extends ServiceImpl<BossRoleMenuMapper, BossRoleMenu> implements IBossRoleMenuService {

}
