BASEURL=http://127.0.0.1:8080

function request(){
    verbe=$1
    ressource=$2
    echo $verbe $ressource
    if [ -n "$3" ]; then
        # extra data as third argument !
        echo "data : " $3
        http_proxy="" curl -X $verbe --data-ascii "$3" $BASEURL$ressource
    else
        http_proxy="" curl -X $verbe $BASEURL$ressource
    fi
    sleep 1
    echo
}

mkdir -p tmp
cd tmp
python -m pyftpdlib -w &
git clone http://github.com/calve/prof .
sleep 3
request "GET" "/"
request "GET" "/run.py"
request "GET" "/legacy/"
request "DELETE" "/run.py"

echo "Should return 404"
request "GET" "/run.py"

request "POST" "/subfolder/" ""
request "GET" "/"
request "POST" "/helloworld" "hello, brigde"
request "GET" "/helloworld"
request "DELETE" "/helloworld"

cd ..
rm -Rvf tmp > /dev/null
kill %1
echo "pyftpdlib killed"
