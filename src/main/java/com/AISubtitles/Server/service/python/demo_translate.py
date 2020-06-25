from tencentcloud.common import credential
from tencentcloud.common.profile.client_profile import ClientProfile
from tencentcloud.common.profile.http_profile import HttpProfile
from tencentcloud.common.exception.tencent_cloud_sdk_exception import TencentCloudSDKException
from tencentcloud.tmt.v20180321 import tmt_client, models

import json
import srt
import re

from sys import argv

ID = "AKIDOtAlnatOK1SwFBjQgj169Fhu1xnPmw5x"
KEY = "sUr2zKPFyzkFWzHMZYIm00xeXCcsugCt"


def translate(source, target, ls):
    while True:
        try:
            cred = credential.Credential(ID, KEY)
            httpProfile = HttpProfile()
            httpProfile.endpoint = "tmt.tencentcloudapi.com"

            clientProfile = ClientProfile()
            clientProfile.httpProfile = httpProfile
            client = tmt_client.TmtClient(cred, "ap-beijing", clientProfile)

            req = models.TextTranslateBatchRequest()
            params = {"Source": source, "Target": target, "ProjectId": 0, "SourceTextList": ls}
            params = json.dumps(params)
            req.from_json_string(params)

            resp = client.TextTranslateBatch(req)
            result = json.loads(resp.to_json_string())
            # print(result)
            ls_ = result.get("TargetTextList", [])
            return ls_

        except TencentCloudSDKException as err:
            err = str(err)
            # 超时就继续
            if 'RequestLimitExceeded' in err:
                continue
            else:
                print(err)
                break


def translate_srt(file_path, new_file_path, source, target):
    # 从file中读取文本，以字符串形式存储到srt_s
    file = open(file_path, mode='r', encoding='utf-8')
    srt_s = file.read()
    file.close()

    # 将srt_s字符串转为包含Subtitle对象的列表subs
    subs = list(srt.parse(srt_s))
    length = len(subs)

    # 将subs里Subtitle对象的content存储到ls，形成['字幕1','字幕2']的格式
    ls = []
    for i in range(length):
        temp_content = re.sub(r'<\/?[\s\S]*?(?:".*")*>', '', subs[i].content)  # 去除所有<>标签
        ls.append(temp_content)

    ls_ = []  # 经过翻译的ls
    # 写这一堆是因为单次请求的字符数不得大于2000
    pre_index = 0
    string = ''
    for i in range(length):
        if len(string + ls[i]) < 2000:
            string = string + ls[i]
            if i == length - 1:
                temp_ls = ls[pre_index:]
                ls_ = ls_ + translate(source, target, temp_ls)
        elif len(ls[i]) >= 2000:
            # print('该段字符数大于2000！')
            break
        else:
            string = ''
            temp_ls = ls[pre_index:i]
            ls_ = ls_ + translate(source, target, temp_ls)
            pre_index = i

    # 更新subs里Subtitle对象的content
    for i in range(length):
        subs[i].content = srt.make_legal_content(ls_[i])
    # subs转为字符串srt_s_
    srt_s_ = srt.compose(subs)

    # 将srt_s_输出到new_file
    new_file = open(new_file_path, 'w+', encoding='utf-8')
    new_file.write(srt_s_)
    new_file.close()


if __name__ == '__main__':
    file_path = argv[1]
    new_file_path = argv[2]
    source = argv[3]
    target = argv[4]
    translate_srt(file_path, new_file_path, source, target)
