package ignore;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 用户组织表，考虑到一个人可以在不同子公司不同部门任职，引入多租户抽出来的。 前端控制器
 * </p>
 *
 * @author john
 * @since 2020-12-10
 */
@Controller
@RequestMapping("/bossUserDepartment")
public class BossUserDepartmentController {

}

