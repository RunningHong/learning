package com.hong.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 输入：n = 3
 输出：[
 "((()))",
 "(()())",
 "(())()",
 "()(())",
 "()()()"
 ]

 卡特兰数的直接表现
 可应用于求解栈的所有入栈和出栈的所有组合
 */
public class GenerateParenthesis {

    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        gengrate(n, n, list, "");
        return list;
    }

    /**
     * left 代表左括号的个数
     * right达标右括号的个数
     * list:代表存放结果的数据结构
     * Str: 代表目前的字符串
     * @author Create by hongzh.zhang on 2020/6/16
     * @param
     * @return
     */
    public void gengrate(int left, int right, List<String> list, String str) {
        if (left == 0 && right == 0) {
            list.add(str);
        }

        // 如果还有左括号可以添加左括号
        if (left>0) {
            gengrate(left - 1, right, list, str + "(");
        }

        // 如果右括号的个数>左括号可以添加右括号
        if (right > left) {
            gengrate(left, right - 1, list, str + ")");
        }
    }

}
