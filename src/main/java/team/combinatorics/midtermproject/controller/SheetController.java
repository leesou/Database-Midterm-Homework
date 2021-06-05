package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.combinatorics.midtermproject.model.dto.AllServiceDTO;
import team.combinatorics.midtermproject.model.dto.CommonResult;
import team.combinatorics.midtermproject.model.dto.ServiceDTO;
import team.combinatorics.midtermproject.service.SheetService;

import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
@RequestMapping("/sheet")
public class SheetController {
    private final SheetService sheetService;

    /**
     * 添加sheet
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CommonResult<Integer> addNewSheet(
            @RequestBody @NotNull(message = "保修单信息不能为空")ServiceDTO serviceDTO
            ) {
        Integer sid = sheetService.addNewSheet(serviceDTO);
        return new CommonResult<>(200, "添加成功", sid);
    }

    /**
     * 更新sheet信息
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CommonResult<String> updateSheet(
            @RequestBody @NotNull(message = "保修单信息不能为空")ServiceDTO serviceDTO
    ) {
        sheetService.updateSheet(serviceDTO);
        return new CommonResult<>(200, "更新成功", "This sheet has been changed.");
    }

    /**
     * 删除sheet
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public CommonResult<String> deleteSheet(
            @RequestParam @NotNull(message = "保修单id不能为空")Integer sid
    ) {
        sheetService.deleteSheet(sid);
        return new CommonResult<>(200, "删除成功", "This sheet has been deleted.");
    }

    /**
     * 查单个sheet的信息
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CommonResult<ServiceDTO> getOneSheet(
            @RequestParam @NotNull(message = "保修单id不能为空")Integer sid
    ) {
        return new CommonResult<>(200, "查询成功", sheetService.getServiceBySid(sid));
    }

    /**
     * 查单个部门的接单信息
     */
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public CommonResult<AllServiceDTO> getDepartmentSheet(
            @RequestParam @NotNull(message = "部门id不能为空")Integer did
    ) {
        return new CommonResult<>(200, "查询成功", sheetService.getServiceByDid(did));
    }

    /**
     * 查单个员工的接单信息
     */
    @RequestMapping(value = "/worker", method = RequestMethod.GET)
    public CommonResult<AllServiceDTO> getWorkerSheet(
            @RequestParam @NotNull(message = "员工id不能为空")Integer wid
    ) {
        return new CommonResult<>(200, "查询成功", sheetService.getServiceByWid(wid));
    }

    /**
     * 查单个user的报修信息
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonResult<AllServiceDTO> getUserSheet(
            @RequestParam @NotNull(message = "用户id不能为空")Integer uid
    ) {
        return new CommonResult<>(200, "查询成功", sheetService.getServiceByUid(uid));
    }

    /**
     * 查所有单
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResult<AllServiceDTO> getAllSheet() {
        return new CommonResult<>(200, "查询成功", sheetService.getAllService());
    }

}
