package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.CommonResult;
import team.combinatorics.midtermproject.model.dto.ManageDTO;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;
import team.combinatorics.midtermproject.service.ManagerService;

import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    /**
     * 添加manager
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public CommonResult<Integer> addNewManager(
            @RequestBody @NotNull(message = "经理信息不能为空")WorkerDTO workerDTO
            ) {
        Integer wid = managerService.addNewManager(workerDTO);
        return new CommonResult<>(200, "添加成功", wid);
    }

    /**
     * 把worker升级为manager
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CommonResult<String> changeWorkerToManager(
            @RequestBody @NotNull(message = "经理信息不能为空") ManageDTO manageDTO
    ) {
        managerService.changeWorkerToManager(manageDTO);
        return new CommonResult<>(200, "添加成功", "This worker has been changed to manager.");
    }

    /**
     * 更新manager信息
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CommonResult<String> updateManager(
            @RequestBody @NotNull(message = "经理信息不能为空")WorkerDTO workerDTO
    ) {
        managerService.updateManager(workerDTO);
        return new CommonResult<>(200, "更新成功", "Manager information has been changed.");
    }

    /**
     * 删除manager
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public CommonResult<String> deleteManager(
            @RequestParam @NotNull(message = "经理的员工id不能为空")Integer wid
    ) {
        managerService.deleteManager(wid);
        return new CommonResult<>(200, "删除成功", "Manager has been deleted.");
    }

    /**
     * 查询部门内的manager
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CommonResult<WorkerDTO> getOneManager(
            @RequestBody @NotNull(message = "部门id不能为空")Integer did
    ) {
        return new CommonResult<>(200, "查询成功", managerService.getManagerByDid(did));
    }

    /**
     * 查询经理管理的员工，员工数目，平均薪水
     */
    @RequestMapping(value = "/worker", method = RequestMethod.GET)
    public CommonResult<AllWorkerDTO> getManagerWorker(
            @RequestBody @NotNull(message = "经理的员工id不能为空")Integer wid
    ) {
        return new CommonResult<>(200, "查询成功", managerService.getWorkerByManager(wid));
    }

    /**
     * 查询所有经理，经理数目，平均薪水
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResult<AllWorkerDTO> getAllManager() {
        return new CommonResult<>(200, "查询成功", managerService.getAllManager());
    }

}
