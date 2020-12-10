package ignore;

import com.william.boss.orm.model.BossDepartment;
import com.william.boss.orm.dao.BossDepartmentMapper;
import ignore.IBossDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织部门表 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossDepartmentServiceImpl extends ServiceImpl<BossDepartmentMapper, BossDepartment> implements IBossDepartmentService {

}
