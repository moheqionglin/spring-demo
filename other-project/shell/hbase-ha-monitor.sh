#!/bin/bash

Hmaster1IP=172.16.8.11
Hmaster2IP=172.16.17.12

Hmaster1URL=$Hmaster1IP:8201
Hmaster2URL=$Hmaster2IP:8201
Hmaster1State=`curl http://$Hmaster1URL/jmx?get=Hadoop:service=HBase,name=Master,sub=Server::tag.isActiveMaster 2>/dev/null |  grep -Po 'isActiveMaster[" :]+\K[^"]+'`
Hmaster2State=`curl http://$Hmaster2URL/jmx?get=Hadoop:service=HBase,name=Master,sub=Server::tag.isActiveMaster 2>/dev/null |  grep -Po 'isActiveMaster[" :]+\K[^"]+'`
activeUrl=''
if [ "$Hmaster1State" == "true" ] ; then
   activeUrl=$Hmaster1URL
elif [ "$Hmaster2State" == "true" ] ; then
   activeUrl=$Hmaster2URL
fi
if [ "$activeUrl" == "" ]; then
   exit 0
fi
echo $activeUrl



cd /home/log/monitor
filename=$(date "+%Y%m%d%H%M%S")
curl http://$activeUrl/master-status#baseStats > "/tmp/$filename"
#获取live的regionserver的个数
liveRegionServers=`curl http://$activeUrl/jmx?get=Hadoop:service=HBase,name=Master,sub=Server::numRegionServers 2>/dev/null |  grep -Po 'numRegionServers[" :]+\K[^"]+'`

#liveRegionServers=3
convertByte(){

	 usize=$1
	 uunit=$2

	 if [ "$uunit" == "TB" ]; then
 			echo $(echo "scale=3; $usize*1024"|bc)
 	 elif [ "$uunit" == "GB" ]; then
 			echo $usize
   elif [ "$uunit" == "MB" ]; then
 			echo $(echo "scale=3; $usize/1024"|bc)
   elif [ "$uunit" == "KB" ];then
 			echo  $(echo "scale=3; $usize/1024/1024"|bc)
   else
 			echo  $(echo "scale=3; $usize/1024/1024/1024"|bc)
   fi
 }


instance_id=locationx-hbase-cluster-dev

date=`date  +"%Y-%m-%d %H:%M.%S"`


httpprex=127.0.0.1:1988

function metric(){
   metric=`echo "[{\
       \"endpoint\"   : \"$2\",\
       \"uniqueKey\"   : \"$2\",\
       \"tags\"       : \"$1\",\
       \"timestamp\"  : $(date +%s),\
       \"metric\"     : \"locationx.hbase.monitor\",\
       \"value\"      : \"$3\",\
       \"counterType\": \"GAUGE\"\
       }] "| python -m json.tool`
       curl -s -m 10 -X POST -d "$metric" "$httpprex/v1/push" &
       echo $metric
}


#########################
#  Base Stats
#  1.  Total request
#  2.  total Regions
#########################

totalRequest=`java -jar xpath.jar //*[@id=\'tab_baseStats\']/table/tbody/tr[$((liveRegionServers + 2))]/td[4]  /tmp/$filename 2>/dev/null`
totalRegion=`java -jar xpath.jar //*[@id=\'tab_baseStats\']/table/tbody/tr[$((liveRegionServers + 2))]/td[5]  /tmp/$filename 2>/dev/null`

for((i=2; i<=$liveRegionServers+1; i++));
		do
#########################
#  Memory
#  1. Used Heap
#  2. Max Heap
#  3. Memstore Size
#########################
			declare -A result=()
			regionser=`java -jar xpath.jar //*[@id=\'tab_memoryStats\']/table/tbody/tr[$i]/td[1]/a /tmp/$filename 2>/dev/null`
			regionser=${regionser%%,*}

			usedHeap=`java -jar xpath.jar //*[@id=\'tab_memoryStats\']/table/tbody/tr[$i]/td[2] /tmp/$filename  2>/dev/null`
			usedHeap=`convertByte $usedHeap`
			result["usedHeap"]=$usedHeap

			maxHeap=`java -jar xpath.jar //*[@id=\'tab_memoryStats\']/table/tbody/tr[$i]/td[3] /tmp/$filename	 2>/dev/null`
			maxHeap=`convertByte $maxHeap`
			result["maxHeap"]=$maxHeap

			memstoreSize=`java -jar xpath.jar //*[@id=\'tab_memoryStats\']/table/tbody/tr[$i]/td[4] /tmp/$filename	2>/dev/null`
			memstoreSize=`convertByte $memstoreSize`
			result["memstoreSize"]=$memstoreSize

#########################
#  Requests
#  1. Request Per Second
#  2. Read Request Count
#  3. Write Request Count
#########################
			requestPerSecond=`java -jar xpath.jar //*[@id=\'tab_requestStats\']/table/tbody/tr[$i]/td[2]  /tmp/$filename  2>/dev/null`
			result["requestPerSecond"]=$requestPerSecond

			readRequestCount=`java -jar xpath.jar //*[@id=\'tab_requestStats\']/table/tbody/tr[$i]/td[3] /tmp/$filename  2>/dev/null`
			result["readRequestCount"]=$readRequestCount

			writeRequestCount=`java -jar xpath.jar //*[@id=\'tab_requestStats\']/table/tbody/tr[2]/td[4] /tmp/$filename  2>/dev/null`
			result["writeRequestCount"]=$writeRequestCount
#########################
#  Storefiles
#  1. Num. Stores
#  2. Num. Storefiles
#  3. Storefile Size Uncompressed
#  4. Storefile Size
#  5. Index Size
#  6. Bloom Size
#########################
			numStores=`java -jar xpath.jar //*[@id=\'tab_storeStats\']/table/tbody/tr[$i]/td[2] /tmp/$filename  2>/dev/null`
			result["numStores"]=$numStores

			numStorefiles=`java -jar xpath.jar //*[@id=\'tab_storeStats\']/table/tbody/tr[$i]/td[3] /tmp/$filename  2>/dev/null`
			result["numStorefiles"]=$numStorefiles

			sfSizeUncompressed=`java -jar xpath.jar //*[@id=\'tab_storeStats\']/table/tbody/tr[$i]/td[4] /tmp/$filename  2>/dev/null`
			sfSizeUncompressed=`convertByte $sfSizeUncompressed`
			result["sfSizeUncompressed"]=$sfSizeUncompressed

			storefileSize=`java -jar xpath.jar //*[@id=\'tab_storeStats\']/table/tbody/tr[$i]/td[5] /tmp/$filename  2>/dev/null`
			storefileSize=`convertByte $storefileSize`
			result["storefileSize"]=$storefileSize

			indexSize=`java -jar xpath.jar //*[@id=\'tab_storeStats\']/table/tbody/tr[$i]/td[6] /tmp/$filename  2>/dev/null`
			indexSize=`convertByte $indexSize`
			result["indexSize"]=$indexSize

			bloomSize=`java -jar xpath.jar //*[@id=\'tab_storeStats\']/table/tbody/tr[$i]/td[7] /tmp/$filename  2>/dev/null`
			bloomSize=`convertByte $bloomSize`
			result["bloomSize"]=$bloomSize

#########################
#  Storefiles
#  1. Num. Compacting KVs
#  2. Num. Compacted KVs
#  3. Remaining KVs
#  4. Compaction Progress
#########################
			numCompactingKVs=`java -jar xpath.jar //*[@id=\'tab_compactStas\']/table/tbody/tr[$i]/td[2] /tmp/$filename  2>/dev/null`
			result["numCompactingKVs"]=$numCompactingKVs

			numCompactedKVs=`java -jar xpath.jar //*[@id=\'tab_compactStas\']/table/tbody/tr[$i]/td[3] /tmp/$filename  2>/dev/null`
			result["numCompactedKVs"]=$numCompactedKVs

			remainingKVs=`java -jar xpath.jar //*[@id=\'tab_compactStas\']/table/tbody/tr[$i]/td[4] /tmp/$filename  2>/dev/null`
			result["remainingKVs"]=$remainingKVs

			compactionProgress=`java -jar xpath.jar //*[@id=\'tab_compactStas\']/table/tbody/tr[$i]/td[5] /tmp/$filename  2>/dev/null`
			compactionProgress=${compactionProgress%\%*}
			result["compactionProgress"]=$compactionProgress


			tags=""
			if [ "$i" == "2" ]; then
				tags=$tags"totalRequest="$totalRequest","
				tags=$tags"totalRegion="$totalRegion","
			fi
			for key in ${!result[@]}
				do
			    tags=$tags"$key="${result[$key]}","
				done

			tags=${tags%,*}

			echo $tags
			metric  $tags $regionser 1
	done;
rm -f /tmp/$filename


