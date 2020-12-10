package ignore;

import com.william.boss.orm.model.BossUserDepartment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。 服务类
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
public interface IBossUserDepartmentService extends IService<BossUserDepartment> {

}
