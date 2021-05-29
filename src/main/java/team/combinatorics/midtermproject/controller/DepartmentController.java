package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.combinatorics.midtermproject.model.dto.AllDepartmentDTO;
import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.CommonResult;
import team.combinatorics.midtermproject.model.dto.DepartmentDTO;
import team.combinatorics.midtermproject.service.DepartmentService;

import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    /**
     * 添加部门
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CommonResult<Integer> addDepartment(
            @RequestBody @NotNull(message = "部门信息不能为空") DepartmentDTO departmentDTO
            ) {
        Integer did = departmentService.addNewDepartment(departmentDTO);
        return new CommonResult<>(200, "添加成功", did);
    }

    /**
     * 修改部门名称
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CommonResult<String> updateDepartment(
            @RequestBody @NotNull(message = "部门信息不能为空") DepartmentDTO departmentDTO
    ) {
        departmentService.updateDepartment(departmentDTO);
        return new CommonResult<>(200, "更新成功", "Department name has been changed.");
    }

    /**
     * 删除部门
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public CommonResult<String> deleteDepartment(
            @RequestParam @NotNull(message = "部门id不能为空") Integer did
    ) {
        departmentService.deleteDepartment(did);
        return new CommonResult<>(200, "删除成功","Department has been deleted.");
    }

    /**
     * 查单个部门，及部门内的经理
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CommonResult<DepartmentDTO> getOneDepartment(
            @RequestParam @NotNull(message = "部门id不能为空") Integer did
    ) {
        return new CommonResult<>(200, "查询成功", departmentService.getDepartmentInfoByDid(did));
    }

    /**
     * 查询所有部门
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResult<AllDepartmentDTO> getAllDepartment() {
        return new CommonResult<>(200, "查询成功", departmentService.getAllDepartment());
    }



    /**
     * 查单个部门内的员工，及平均薪水
     */
    @RequestMapping(value = "/worker", method = RequestMethod.GET)
    public CommonResult<AllWorkerDTO> getOneDepartmentWorker(
            @RequestParam @NotNull(message = "部门id不能为空") Integer did
    ) {
        return new CommonResult<>(200, "查询成功", departmentService.getDepartmentWorkerByDid(did));
    }

}
