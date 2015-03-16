BASEURL=http://127.0.0.1:8080

function request(){
    verbe=$1
    ressource=$2
    echo $verbe $ressource
    curl -X $verbe $BASEURL$ressource
}

git clone http://github.com/calve/prof tmp
cd tmp
python -m pyftpdlib -w &
sleep 3
request "GET" "/"
request "GET" "/run.py"
request "GET" "/src/"
request "DELETE" "/run.py"

echo "Should return 404"
request "GET" "/run.py"

cd ..
rm -Rvf tmp > /dev/null
kill %1
echo "pyftpdlib killed"
