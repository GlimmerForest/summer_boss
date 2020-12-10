package ignore;

import com.william.boss.orm.model.BossUserDepartment;
import com.william.boss.orm.dao.BossUserDepartmentMapper;
import ignore.IBossUserDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。 服务实现类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Service
public class BossUserDepartmentServiceImpl extends ServiceImpl<BossUserDepartmentMapper, BossUserDepartment> implements IBossUserDepartmentService {

}
