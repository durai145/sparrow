action=$1
username=$2
password=$3
path=$4
filePath=$5

STATUS="status"
START="start"
STOP="stop"

function usage {
	echo "usage"
}


if [ "$action" == "start" ]
then 

	test  $filePath
	if [ $? == 0 ] 
	then 
		curl -u "$username:$password" -T $filePath  "http://localhost:8080/manager/text/deploy?path=/$path"

	else
		echo "File is not found"	
		exit 1
	fi

fi

if [ "$action" == "stop" ]
then 
	curl -u "$username:$password"  "http://localhost:8080/manager/text/undeploy?path=/$path"
fi

if [ "$action" == "status" ]
then 
	curl -u "$username:$password"  "http://localhost:8080/manager/text/list"
fi
