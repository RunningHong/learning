# -*-coding:utf-8-*-

import json

def main():
    json_data_str= '''
    [
        {
            'biz_name' : ['aaa'],
            'report_alias' : '消费'
        }
    ]
    '''


    # 上面的字符串非json字符串，所以这里转换为json字符串
    json_str=json_data_str.replace("\'", '\"')
    # print(json_str)

    # 将json字符串
    json_object=json.loads(json_str)
    print(json_object)



if __name__ == '__main__':
    main()
