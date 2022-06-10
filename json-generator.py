from datetime import datetime
import os
import random
import datetime
import threading
import time
import sys
import json
import string

# intially making directory
parent_dir=''
dir_name='gen-'+str(datetime.datetime.now())
path=os.path.join(parent_dir,dir_name)

os.mkdir(path)

print("Generated ",dir_name)

list_activities=["doubleTap","singleTap","crash","anr","invalid"]




#generate json content
def generate_json_content():
    str={}
    
    str["uniqueId"]=random.getrandbits(100)
    
    activities=[]
    for i in range(10):

        r=random.randint(0,len(list_activities))   

        act={}
        act["name"]=list_activities[r]
        time_val=0-random.random()*10009 if random.randint(0,3)>2  else time.time()
        act["time"]=time_val
        duration=int(0-random.random()*10009) if random.randint(0,30000)>100000   else int(random.randint(0,30000))
        act["duration"]=duration
        

        activities.append(act)

    str["activities"]=activities
    return json.dumps(str)

# create required files 
def create_file():
    file_name='file-'+str(round(time.time()*10000))
    __location__=os.path.realpath(os.path.join(os.getcwd(),dir_name))
    print(__location__)
    # do smth for files
    with open(os.path.join(__location__,file_name),"w") as file_ready_to_write:
        file_ready_to_write.write(generate_json_content())

    # print('hello')


# main 
no_of_threads=sys.argv[1] if len(sys.argv)>0  else 1000

threads=[]

for i in range(int(no_of_threads)):
    thread=threading.Thread(target=create_file)
    threads.append(thread)
    thread.start()
