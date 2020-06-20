package com.AISubtitles.Server.service;

import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 18:07 2020/6/18
 * @ Description：以命令行的形式来执行python脚本
 * @ Modified By：
 * @Version: 1.0$
 */
@Service
public class ExecuteCommandService {
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
