package com.AISubtitles.Server.utils;

import java.io.*;
import java.util.*;

class ExecuteCommand {
    /**
     * 执行命令行
     * @author PY
     * @param commList 命令列表
     */
    public static void exec(final List<String> commList) {
        final ProcessBuilder processBuilder = new ProcessBuilder(commList);
        processBuilder.redirectErrorStream(true);
        try {
            final Process process = processBuilder.start();
            final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            final int exitCode = process.waitFor();
            System.out.println("exitCode = " + exitCode);
            process.destroy();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}