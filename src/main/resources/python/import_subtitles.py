from ffmpy3 import FFmpeg
from sys import argv

if __name__ == '__main__':
    input_path = argv[1]
    subtitles_path = argv[2]
    output_path = argv[3]
    ff = FFmpeg(
        inputs={input_path: None},
        outputs={output_path: ['-vf', 'subtitles='+subtitles_path, '-y']}
    )
    ff.run()
