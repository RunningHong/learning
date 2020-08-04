# -*-coding:utf-8-*-

if __name__ == '__main__':
    str="hello this is tom speaking"

    print(str[0:4]) # hell
    print(str[0:5]) # hello
    print(str[1:3]) # el

    # 从某个位置到最后
    print(str[0:]) # hello this is tom speaking

    # 导数字符
    print(str[0:-1]) # hello this is tom speakin
    print(str[0:-2]) # hello this is tom speaki

    # 从索引3开始每隔4个字符取出一个，直到索引22
    print(str[3: 20: 4]) # lhiop


    # 获取从索引5开始，直到末尾的子串
    print(str[7: ]) # his is tom speaking
    # 获取从索引-21开始，直到末尾的子串
    print(str[-21: ]) #  this is tom speaking
    # 从开头截取字符串，直到索引22为止
    print(str[: 22]) # hello this is tom spea
    # 每隔3个字符取出一个字符
    print(str[:: 3]) # hltssosan
