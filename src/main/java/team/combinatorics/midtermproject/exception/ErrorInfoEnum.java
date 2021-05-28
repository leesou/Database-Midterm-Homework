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
    WORKER_UPDATE_ERROR(40006, "更新员工信息时出错，请检查员工wid是否正确"),
    WORKER_DELETE_ERROR(40007, "删除员工时出错，请检查员工wid是否正确"),
    WORKER_SELECT_ERROR(40008, "查询员工时出错，请检查员工wid是否正确");

    private final Integer errCode;
    private final String errMsg;
}
