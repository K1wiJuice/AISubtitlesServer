from ffmpy3 import FFmpeg
from sys import argv

if __name__ == '__main__':
    input_path = argv[1]
    output_path = argv[2]
    b = argv[3]
    ff = FFmpeg(
        inputs={input_path: None},
        outputs={output_path: ['-b', str(b)+'k', '-y']}#-b视频码率 k是单位 -y覆盖输出文件
    )
    ff.run()
