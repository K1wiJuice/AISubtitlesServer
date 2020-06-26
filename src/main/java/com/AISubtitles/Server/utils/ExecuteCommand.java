package com.AISubtitles.Server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ExecuteCommand {
    /**
     * 执行命令行
     *
     * @param commList 命令列表
     * @return 执行命令后终端的输出
     * @author PY
     */
    public static String exec(final List<String> commList) {
        final ProcessBuilder processBuilder = new ProcessBuilder(commList);
        processBuilder.redirectErrorStream(true);
        StringBuffer sb = new StringBuffer();
        try {
            final Process process = processBuilder.start();
            final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
            final String exitCode = "exitCode = " + process.waitFor() + ";";
            System.out.println(exitCode);
            sb.append(exitCode);
            process.destroy();
        } catch (final IOException e) {
            e.printStackTrace();
            sb.append("IOException;");
        } catch (final InterruptedException e) {
            e.printStackTrace();
            sb.append("InterruptedException;");
        }
        return sb.toString();
    }
}