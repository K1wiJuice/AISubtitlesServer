package com.AISubtitles.Server.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FFmpegJ {

    private final List<String> commList;

    /**
     * @param globals 全局变量
     * @param inputs  输入变量
     * @param outputs 输出变量
     * @author PY
     */
    public FFmpegJ(final List<String> globals, final Map<String, List<String>> inputs,
                   final Map<String, List<String>> outputs) {
        this.commList = new ArrayList<>();
        this.commList.add("ffmpeg");

        for (final String opt : globals) {
            this.commList.add(opt);
        }

        final Set<String> inputKeys = inputs.keySet();
        final Iterator<String> it1 = inputKeys.iterator();
        while (it1.hasNext()) {
            final String key = it1.next();
            final List<String> options = inputs.get(key);
            for (final String opt : options) {
                this.commList.add(opt);
            }
            this.commList.add("-i");
            this.commList.add(key);
        }

        final Set<String> outputKeys = outputs.keySet();
        final Iterator<String> it2 = outputKeys.iterator();
        while (it2.hasNext()) {
            final String key = it2.next();
            final List<String> options = outputs.get(key);
            for (final String opt : options) {
                this.commList.add(opt);
            }
            this.commList.add(key);
        }

    }

    /**
     * 返回生成的命令字符串
     *
     * @return 生成的命令
     * @author PY
     */
    public String cmd() {
        String command = "";
        for (final String comm : this.commList) {
            command += comm + " ";
        }
        return command;
    }

    /**
     * 执行命令
     *
     * @return 指令是否执行成功
     */
    public Boolean run() {
        String res = ExecuteCommand.exec(this.commList);
        String regexExitCode = "exitCode = (\\d+);";
        Pattern pattern = Pattern.compile(regexExitCode);
        Matcher m = pattern.matcher(res);
        int exitCode = 1;
        if (m.find()) {
            exitCode = Integer.valueOf(m.group(1));
        }
        return exitCode == 0 ? true : false;
    }

}
