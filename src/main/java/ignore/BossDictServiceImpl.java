package ignore;

import com.william.boss.orm.model.BossDict;
import com.william.boss.orm.dao.BossDictMapper;
import ignore.IBossDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典码表 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossDictServiceImpl extends ServiceImpl<BossDictMapper, BossDict> implements IBossDictService {

}
