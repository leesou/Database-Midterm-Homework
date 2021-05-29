package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.combinatorics.midtermproject.model.dto.CommonResult;
import team.combinatorics.midtermproject.model.dto.UserDTO;
import team.combinatorics.midtermproject.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    /**
     * 添加user
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CommonResult<Integer> addNewUser(
            @RequestBody @NotNull(message = "用户信息不能为空")UserDTO userDTO
            ) {
        Integer uid = userService.addNewUser(userDTO);
        return new CommonResult<>(200, "添加成功", uid);
    }


    /**
     * 修改user信息
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CommonResult<String> updateUser(
            @RequestBody @NotNull(message = "用户信息不能为空")UserDTO userDTO
    ) {
        userService.updateUser(userDTO);
        return new CommonResult<>(200, "更新成功", "User information has been changed.");
    }

    /**
     * 删除user
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public CommonResult<String> deleteUser(
            @RequestParam @NotNull(message = "用户信息不能为空")Integer uid
    ) {
        userService.deleteUserByUid(uid);
        return new CommonResult<>(200, "删除成功", "This user has been deleted.");
    }

    /**
     * 查单个user的信息
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CommonResult<UserDTO> getOneUser(
            @RequestParam @NotNull(message = "用户信息不能为空")Integer uid
    ) {
        return new CommonResult<>(200, "查询成功", userService.getUserByUid(uid));
    }

    /**
     * 查所有user
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResult<List<UserDTO>> getAllUser() {
        return new CommonResult<>(200, "查询成功", userService.getAllUser());
    }

}
