#!/bin/bash
cat /proc/meminfo | awk '{
	if($1=="MemTotal:"){
		total=$2
	}
	else if($1=="MemFree:"){
		free=100*($2/total)
		printf "free=%0.2f\n",free
	}
	else if($1=="MemAvailable:"){
		avail=100*($2/total)
		printf "avail=%0.2f\n",avail
	}
	else if($1=="Buffers:"){
		buff=100*($2/total)
		printf "buffer=%0.2f\n",buff
	}
	else if($1=="Cached:"){
		catched=100*($2/total)
		printf "catched=%0.2f\n",catched
	}

}'
