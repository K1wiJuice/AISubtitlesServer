import srt

from sys import argv


def merge_srt(zh_file_path, en_file_path, new_file_path):
    zh_file = open(zh_file_path, mode='r', encoding='utf-8')
    zh_srt_s = zh_file.read()
    zh_file.close()
    en_file = open(en_file_path, mode='r', encoding='utf-8')
    en_srt_s = en_file.read()
    en_file.close()

    zh_subs = list(srt.parse(zh_srt_s))
    zh_length = len(zh_subs)
    en_subs = list(srt.parse(en_srt_s))
    en_length = len(en_subs)
    if zh_length != en_length:
        return False

    subs = []
    for i in range(zh_length):
        temp_subtitle = zh_subs[i]
        temp_subtitle.content = temp_subtitle.content + '\n' + en_subs[i].content
        temp_subtitle.content = srt.make_legal_content(temp_subtitle.content)
        subs.append(temp_subtitle)

    srt_s = srt.compose(subs)

    # 将srt_s输出到new_file
    new_file = open(new_file_path, 'w+', encoding='utf-8')
    new_file.write(srt_s)
    new_file.close()

    return True


if __name__ == '__main__':
    zh_file_path = argv[1]
    en_file_path = argv[2]
    new_file_path = argv[3]
    merge_srt(zh_file_path, en_file_path, new_file_path)
