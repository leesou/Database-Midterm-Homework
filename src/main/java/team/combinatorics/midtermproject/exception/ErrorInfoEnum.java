package team.combinatorics.midtermproject.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ErrorInfoEnum {
    DEPARTMENT_INSERT_ERR(40001, "插入部门时出错，请检查是否已有同名部门"),
    DEPARTMENT_UPDATE_ERR(40002, "更新部门时出错，请检查did是否正确"),
    DEPARTMENT_DELETE_ERROR(40003, "删除部门时出错，请检查did是否正确"),
    DEPARTMENT_SELECT_ERROR(40004, "查询部门信息时出错，请检查did是否正确"),
    WORKER_INSERT_ERROR(40005, "添加员工时出错，请检查员工所属部门的did是否正确"),
    WORKER_UPDATE_ERROR(40006, "更新员工信息时出错，请检查员工wid/did是否正确"),
    WORKER_DELETE_ERROR(40007, "删除员工时出错，请检查员工wid是否正确"),
    WORKER_SELECT_ERROR(40008, "查询员工时出错，请检查员工wid是否正确"),
    MANAGER_INSERT_ERROR(40009, "添加经理时出错，请检查did/wid是否正确"),
    MANAGER_UPDATE_ERROR(40010, "更新经理时出错，请检查did/wid是否正确"),
    MANAGER_DELETE_ERROR(40011, "删除经理时出错，请检查did/wid是否正确"),
    MANAGER_SELECT_ERROR(40012, "查询经理时出错，请检查did/wid是否正确"),
    USER_INSERT_ERROR(40013, "添加用户时出错"),
    USER_UPDATE_ERROR(40014, "更新用户信息时出错，请检查uid是否正确"),
    USER_DELETE_ERROR(40015, "删除用户时出错，请检查uid是否正确"),
    USER_SELECT_ERROR(40016, "查询用户时出错，请检查uid是否正确"),
    PRODUCT_INSERT_ERROR(40017, "添加商品时出错，请检查uid和保修时间是否正确"),
    PRODUCT_UPDATE_ERROR(40018, "更新商品信息时出错，请检查pid和保修时间是否正确"),
    PRODUCT_DELETE_ERROR(40019, "删除商品时出错，请检查pid是否正确"),
    PRODUCT_SELECT_ERROR(40020, "查询商品时出错，请检查pid是否正确"),
    SHEET_INSERT_ERROR(40021, "添加报修记录时出错，请检查wid和pid是否正确"),
    SHEET_UPDATE_ERROR(40022, "更新商品信息时出错，请检查wid和pid是否正确"),
    SHEET_DELETE_ERROR(40023, "删除商品时出错，请检查sid/wid/pid/uid是否正确"),
    SHEET_SELECT_ERROR(40024, "查询商品时出错，请检查sid/wid/pid/uid是否正确"),
    WORKER_SHEET_ERROR(40025, "删除员工/经理时出错，仍有未交接的维修单"),
    SHEET_WID_ERROR(40026, "输入的员工id不存在");

    private final Integer errCode;
    private final String errMsg;
}
