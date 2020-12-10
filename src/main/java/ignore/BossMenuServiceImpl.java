package ignore;

import com.william.boss.orm.model.BossMenu;
import com.william.boss.orm.dao.BossMenuMapper;
import ignore.IBossMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossMenuServiceImpl extends ServiceImpl<BossMenuMapper, BossMenu> implements IBossMenuService {

}
