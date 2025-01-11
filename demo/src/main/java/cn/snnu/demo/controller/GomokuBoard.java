package cn.snnu.demo.controller;

import org.springframework.stereotype.Component;

@Component
public class GomokuBoard{
    // 棋盘大小，这里以常见的15×15为例
    private static final int BOARD_SIZE = 15;
    // 棋盘二维数组，0表示空位，1表示玩家落子，2表示电脑落子
    private int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

    // 判断是否有玩家获胜
    public boolean isPlayerWin() {
        return checkWin(1);
    }

    // 判断是否有电脑获胜
    public boolean isComputerWin() {
        return checkWin(2);
    }

    // 通用的检查是否某方获胜的方法
    private boolean checkWin(int player) {
        // 横向检查
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE - 4; col++) {
                if (board[row][col] == player && board[row][col + 1] == player &&
                        board[row][col + 2] == player && board[row][col + 3] == player &&
                        board[row][col + 4] == player) {
                    return true;
                }
            }
        }

        // 纵向检查
        for (int col = 0; col < BOARD_SIZE; col++) {
            for (int row = 0; row < BOARD_SIZE - 4; row++) {
                if (board[row][col] == player && board[row + 1][col] == player &&
                        board[row + 2][col] == player && board[row + 3][col] == player &&
                        board[row + 4][col] == player) {
                    return true;
                }
            }
        }

        // 正斜向（从左上角到右下角方向）检查
        for (int row = 0; row < BOARD_SIZE - 4; row++) {
            for (int col = 0; col < BOARD_SIZE - 4; col++) {
                if (board[row][col] == player && board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player && board[row + 3][col + 3] == player &&
                        board[row + 4][col + 4] == player) {
                    return true;
                }
            }
        }

        // 反斜向（从右上角到左下角方向）检查
        for (int row = 4; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE - 4; col++) {
                if (board[row][col] == player && board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player && board[row - 3][col + 3] == player &&
                        board[row - 4][col + 4] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    // 简单的局势评估函数，这里只是简单示意，可以根据实际情况更细化完善
    private int evaluate() {
        // 可以添加各种局面情况的分值计算逻辑，比如连续子的数量等影响因素
        return 0;
    }

    // 极小极大算法结合α-β剪枝
    private int minimax(int depth, int alpha, int beta, boolean isMaximizing) {
        if (depth == 0 || isPlayerWin() || isComputerWin()) {
            return evaluate();
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (board[row][col] == 0) {
                        board[row][col] = 2; // 模拟电脑落子
                        int eval = minimax(depth - 1, alpha, beta, false);
                        board[row][col] = 0; // 回溯，撤销落子
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    if (board[row][col] == 0) {
                        board[row][col] = 1; // 模拟玩家落子
                        int eval = minimax(depth - 1, alpha, beta, true);
                        board[row][col] = 0; // 回溯，撤销落子
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }

    // 电脑落子的方法
    public void computerMove() {
        int bestRow = -1;
        int bestCol = -1;
        int bestScore = Integer.MIN_VALUE;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 0) {
                    board[row][col] = 2;
                    int score = minimax(3, Integer.MIN_VALUE, Integer.MAX_VALUE, false); // 这里深度设为3举例，可调整
                    board[row][col] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = row;
                        bestCol = col;
                    }
                }
            }
        }
        board[bestRow][bestCol] = 2;
    }

    // 获取棋盘当前状态
    public int[][] getBoard() {
        return board;
    }

    // 处理玩家落子操作
    public void playerMove(int row, int col) {
        board[row][col] = 1;
    }
}