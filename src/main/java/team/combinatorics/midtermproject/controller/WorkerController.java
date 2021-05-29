package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.CommonResult;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;
import team.combinatorics.midtermproject.service.WorkerService;

import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
@RequestMapping("/worker")
public class WorkerController {
    private final WorkerService workerService;

    /**
     * 添加worker
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CommonResult<Integer> addNewWorker(
            @RequestBody @NotNull(message = "员工信息不能为空") WorkerDTO workerDTO
            ) {
        Integer wid = workerService.addNewWorker(workerDTO);
        return new CommonResult<>(200, "添加成功", wid);
    }

    /**
     * 更新非manager的worker信息
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CommonResult<String> updateWorker(
            @RequestBody @NotNull(message = "员工信息不能为空") WorkerDTO workerDTO
    ) {
        workerService.updateWorker(workerDTO);
        return new CommonResult<>(200, "更新成功", "Worker's information has been updated.");
    }

    /**
     * 删除非manager的worker
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public CommonResult<String> deleteWorker(
            @RequestParam @NotNull(message = "员工id不能为空") Integer wid
    ) {
        workerService.deleteWorker(wid);
        return new CommonResult<>(200, "删除成功", "The worker has been deleted.");
    }

    /**
     * 查询单个worker的信息
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CommonResult<WorkerDTO> getOneWorker(
            @RequestParam @NotNull(message = "员工id不能为空") Integer wid
    ) {
        return new CommonResult<>(200, "查询成功", workerService.getWorkerInfoByWid(wid));
    }

    /**
     * 查询单个worker的manager信息
     */
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public CommonResult<WorkerDTO> getOneWorkerManager(
            @RequestParam @NotNull(message = "员工id不能为空") Integer wid
    ) {
        return new CommonResult<>(200, "查询成功", workerService.getWorkerManagerByWid(wid));
    }

    /**
     * 查询所有员工的信息，总个数，平均薪水
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResult<AllWorkerDTO> getAllWorker() {
        return new CommonResult<>(200, "查询成功", workerService.getAllWorker());
    }

}
