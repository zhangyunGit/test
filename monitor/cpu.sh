#!/bin/bash

function cpuinfo(){
	cat /proc/stat | grep "^cpu " | awk '
	{
		total=$2+$3+$4+$5+$6+$7+$8+$9+$10+$11
		print total" "$5" "$2" "$4" "$6
	}'
}

cpu1=( $(cpuinfo) )
sleep 0.2
cpu2=( $(cpuinfo) )

total=$(( ${cpu2[0]}-${cpu1[0]} ))
free=$(( ${cpu2[1]}-${cpu1[1]} ))
usr=$(( ${cpu2[2]}-${cpu1[2]} ))
sys=$(( ${cpu2[3]}-${cpu1[3]} ))
io=$(( ${cpu2[4]}-${cpu1[4]} ))

echo "$total $free $usr $sys $io" | awk '{
	free=100*($2/$1)
	usr=100*($3/$1)
	sys=100*($4/$1)
	io=100*($5/$1)
	printf "free=%.2f\nusr=%.2f\nsys=%.2f\nio=%.2f\n",free,usr,sys,io
}'
