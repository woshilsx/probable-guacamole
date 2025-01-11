package cn.snnu.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gomoku")
public class GomokuController {

    @Autowired
    private GomokuBoard gomokuBoard;

    // 获取当前棋盘状态，前端可以通过这个接口来更新展示的棋盘
    @GetMapping("/board")
    public int[][] getBoard() {
        return gomokuBoard.getBoard();
    }

    // 处理玩家落子请求
    @PostMapping("/playerMove")
    public void playerMove(@RequestParam("row") int row, @RequestParam("col") int col) {
        gomokuBoard.playerMove(row, col);
        if (!gomokuBoard.isPlayerWin()) {
            gomokuBoard.computerMove();
        }
    }

    // 检查是否游戏结束（玩家或者电脑获胜）
    @GetMapping("/isGameOver")
    public boolean isGameOver() {
        return gomokuBoard.isPlayerWin() || gomokuBoard.isComputerWin();
    }
}